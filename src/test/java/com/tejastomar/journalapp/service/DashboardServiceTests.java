package com.tejastomar.journalapp.service;

import com.tejastomar.journalapp.dto.DashboardResponseDTO;
import com.tejastomar.journalapp.entity.JournalEntry;
import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.enums.Sentiment;
import com.tejastomar.journalapp.services.DashboardService;
import com.tejastomar.journalapp.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void getDashboard_returnsExpectedValuesForJournalEntries() {
        LocalDate today = LocalDate.now();
        List<JournalEntry> entries = List.of(
                entry(today, Sentiment.HAPPY),
                entry(today.minusDays(1), Sentiment.SAD),
                entry(today.minusDays(2), Sentiment.HAPPY),
                entry(today.minusMonths(1), Sentiment.ANGRY)
        );
        when(userService.findByUserName("tejas")).thenReturn(user("tejas", entries));

        DashboardResponseDTO dashboard = dashboardService.getDashboard("tejas");

        assertEquals("tejas", dashboard.getUsername());
        assertEquals(4, dashboard.getTotalEntries());
        assertEquals(3, dashboard.getCurrentStreak());
        assertEquals(expectedEntriesThisWeek(entries, today), dashboard.getEntriesThisWeek());
        assertEquals(expectedEntriesThisMonth(entries, today), dashboard.getEntriesThisMonth());
        assertEquals(Sentiment.HAPPY, dashboard.getDominantSentiment());
        assertEquals(today, dashboard.getLastEntryDate());
    }

    @Test
    void getDashboard_countsThisWeekFromMondayInsteadOfLastSevenDays() {
        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        List<JournalEntry> entries = List.of(
                entry(monday, Sentiment.HAPPY),
                entry(today, Sentiment.SAD),
                entry(monday.minusDays(1), Sentiment.ANGRY)
        );
        when(userService.findByUserName("tejas")).thenReturn(user("tejas", entries));

        DashboardResponseDTO dashboard = dashboardService.getDashboard("tejas");

        assertEquals(2, dashboard.getEntriesThisWeek());
    }

    @Test
    void getDashboard_keepsYesterdayStreakActiveUntilTodayIsMissed() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        when(userService.findByUserName("tejas"))
                .thenReturn(user("tejas", List.of(entry(yesterday, Sentiment.HAPPY))));

        DashboardResponseDTO dashboard = dashboardService.getDashboard("tejas");

        assertEquals(1, dashboard.getCurrentStreak());
    }

    @Test
    void getDashboard_returnsEmptyDashboardForNewUser() {
        when(userService.findByUserName("new-user")).thenReturn(user("new-user", null));

        DashboardResponseDTO dashboard = dashboardService.getDashboard("new-user");

        assertEquals("new-user", dashboard.getUsername());
        assertEquals(0, dashboard.getTotalEntries());
        assertEquals(0, dashboard.getCurrentStreak());
        assertEquals(0, dashboard.getEntriesThisWeek());
        assertEquals(0, dashboard.getEntriesThisMonth());
        assertNull(dashboard.getDominantSentiment());
        assertNull(dashboard.getLastEntryDate());
    }

    private User user(String username, List<JournalEntry> entries) {
        User user = new User();
        user.setUserName(username);
        user.setJournalEntries(entries);
        return user;
    }

    private JournalEntry entry(LocalDate date, Sentiment sentiment) {
        JournalEntry entry = new JournalEntry();
        entry.setDate(date.atStartOfDay());
        entry.setSentiment(sentiment);
        return entry;
    }

    private int expectedEntriesThisWeek(List<JournalEntry> entries, LocalDate today) {
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        return (int) entries.stream()
                .filter(entry -> !entry.getDate().toLocalDate().isBefore(monday))
                .filter(entry -> !entry.getDate().toLocalDate().isAfter(today))
                .count();
    }

    private int expectedEntriesThisMonth(List<JournalEntry> entries, LocalDate today) {
        return (int) entries.stream()
                .filter(entry -> entry.getDate().getMonth() == today.getMonth())
                .filter(entry -> entry.getDate().getYear() == today.getYear())
                .filter(entry -> !entry.getDate().toLocalDate().isAfter(today))
                .count();
    }
}
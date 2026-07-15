package com.tejastomar.journalapp.service;

import com.tejastomar.journalapp.entity.JournalEntry;
import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.enums.Sentiment;
import com.tejastomar.journalapp.services.JournalSearchService;
import com.tejastomar.journalapp.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JournalSearchServiceTests {

    @Mock
    private UserService userService;

    @InjectMocks
    private JournalSearchService journalSearchService;

    @Test
    void searchJournalEntries_appliesTitleSentimentDateRangeAndDescendingSort() {
        User user = user("tejas", List.of(
                entry("Morning Reflection", Sentiment.HAPPY, LocalDate.of(2026, 7, 10)),
                entry("Evening reflection", Sentiment.HAPPY, LocalDate.of(2026, 7, 12)),
                entry("Work reflection", Sentiment.SAD, LocalDate.of(2026, 7, 13)),
                entry("Old reflection", Sentiment.HAPPY, LocalDate.of(2026, 6, 10))
        ));
        when(userService.findByUserName("tejas")).thenReturn(user);

        List<JournalEntry> results = journalSearchService.searchJournalEntries(
                "tejas", "REFLECTION", Sentiment.HAPPY,
                LocalDate.of(2026, 7, 9), LocalDate.of(2026, 7, 12), "desc"
        );

        assertEquals(2, results.size());
        assertEquals("Evening reflection", results.get(0).getTitle());
        assertEquals("Morning Reflection", results.get(1).getTitle());
        verify(userService).findByUserName("tejas");
    }

    @Test
    void searchJournalEntries_usesDescendingSortByDefaultAndSupportsAscendingSort() {
        User user = user("tejas", List.of(
                entry("Old", Sentiment.HAPPY, LocalDate.of(2026, 7, 10)),
                entry("New", Sentiment.SAD, LocalDate.of(2026, 7, 12))
        ));
        when(userService.findByUserName("tejas")).thenReturn(user);

        List<JournalEntry> descending = journalSearchService.searchJournalEntries(
                "tejas", null, null, null, null, null
        );
        List<JournalEntry> ascending = journalSearchService.searchJournalEntries(
                "tejas", null, null, null, null, "asc"
        );

        assertEquals("New", descending.get(0).getTitle());
        assertEquals("Old", ascending.get(0).getTitle());
    }

    @Test
    void searchJournalEntries_rejectsInvalidDateRangeAndSortDirection() {
        when(userService.findByUserName("tejas")).thenReturn(user("tejas", List.of()));

        assertThrows(IllegalArgumentException.class, () -> journalSearchService.searchJournalEntries(
                "tejas", null, null, LocalDate.of(2026, 7, 12), LocalDate.of(2026, 7, 10), "desc"
        ));
        assertThrows(IllegalArgumentException.class, () -> journalSearchService.searchJournalEntries(
                "tejas", null, null, null, null, "newest"
        ));
    }

    private User user(String username, List<JournalEntry> entries) {
        User user = new User();
        user.setUserName(username);
        user.setJournalEntries(entries);
        return user;
    }

    private JournalEntry entry(String title, Sentiment sentiment, LocalDate date) {
        JournalEntry entry = new JournalEntry();
        entry.setTitle(title);
        entry.setSentiment(sentiment);
        entry.setDate(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 12, 0));
        return entry;
    }
}

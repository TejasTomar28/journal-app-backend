package com.tejastomar.journalapp.services;

import com.tejastomar.journalapp.dto.DashboardResponseDTO;
import com.tejastomar.journalapp.entity.JournalEntry;
import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.enums.Sentiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DashboardService {

    @Autowired
    private UserService userService;

    public DashboardResponseDTO getDashboard(String username) {

        User user = userService.findByUserName(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }

        List<JournalEntry> entries = user.getJournalEntries();
        if (entries == null) {
            entries = Collections.emptyList();
        }

        int totalEntries = entries.size();
        int currentStreak = calculateCurrentStreak(entries);
        int weeklyEntries = calculateWeeklyEntries(entries);
        int monthlyEntries = calculateMonthlyEntries(entries);

        Sentiment dominantSentiment = calculateDominantSentiment(entries);

        LocalDate lastEntryDate = getLastEntryDate(entries);

        return DashboardResponseDTO.builder()
                .username(user.getUserName())
                .totalEntries(totalEntries)
                .currentStreak(currentStreak)
                .entriesThisWeek(weeklyEntries)
                .entriesThisMonth(monthlyEntries)
                .dominantSentiment(dominantSentiment)
                .lastEntryDate(lastEntryDate)
                .build();
    }

    private int calculateCurrentStreak(List<JournalEntry> entries) {
        if (entries == null || entries.isEmpty()) {
            return 0;
        }

        Set<LocalDate> journalDates = new HashSet<>();
        LocalDate today = LocalDate.now();

        for (JournalEntry entry : entries) {
            if (entry == null || entry.getDate() == null) {
                continue;
            }

            LocalDate entryDate = entry.getDate().toLocalDate();
            if (!entryDate.isAfter(today)) {
                journalDates.add(entryDate);
            }
        }

        // The streak remains active until the user misses a complete day.
        LocalDate currentDate = journalDates.contains(today) ? today : today.minusDays(1);
        int streak = 0;

        while (journalDates.contains(currentDate)) {
            streak++;
            currentDate = currentDate.minusDays(1);
        }

        return streak;
    }

    private int calculateWeeklyEntries(List<JournalEntry> entries) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        int count = 0;

        for (JournalEntry entry : entries) {
            if (entry == null || entry.getDate() == null) {
                continue;
            }

            LocalDate entryDate = entry.getDate().toLocalDate();
            if (!entryDate.isBefore(startOfWeek) && !entryDate.isAfter(today)) {
                count++;
            }
        }

        return count;
    }

    private int calculateMonthlyEntries(List<JournalEntry> entries) {
        LocalDate today = LocalDate.now();
        int count = 0;

        for (JournalEntry entry : entries) {
            if (entry == null || entry.getDate() == null) {
                continue;
            }

            LocalDate entryDate = entry.getDate().toLocalDate();
            if (entryDate.getMonth() == today.getMonth()
                    && entryDate.getYear() == today.getYear()
                    && !entryDate.isAfter(today)) {
                count++;
            }
        }

        return count;
    }

    private Sentiment calculateDominantSentiment(List<JournalEntry> entries) {
        Map<Sentiment, Integer> sentimentCount = new HashMap<>();

        for (JournalEntry entry : entries) {
            if (entry == null || entry.getSentiment() == null) {
                continue;
            }

            Sentiment sentiment = entry.getSentiment();
            sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
        }

        Sentiment dominantSentiment = null;
        int highestCount = 0;

        // Iterating enum values gives a predictable result if two counts tie.
        for (Sentiment sentiment : Sentiment.values()) {
            int count = sentimentCount.getOrDefault(sentiment, 0);
            if (count > highestCount) {
                highestCount = count;
                dominantSentiment = sentiment;
            }
        }

        return dominantSentiment;
    }

    private LocalDate getLastEntryDate(List<JournalEntry> entries) {
        LocalDate lastEntryDate = null;
        LocalDate today = LocalDate.now();

        for (JournalEntry entry : entries) {
            if (entry == null || entry.getDate() == null) {
                continue;
            }

            LocalDate entryDate = entry.getDate().toLocalDate();
            if (!entryDate.isAfter(today)
                    && (lastEntryDate == null || entryDate.isAfter(lastEntryDate))) {
                lastEntryDate = entryDate;
            }
        }

        return lastEntryDate;
    }
}

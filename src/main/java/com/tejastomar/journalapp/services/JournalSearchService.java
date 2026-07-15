package com.tejastomar.journalapp.services;

import com.tejastomar.journalapp.entity.JournalEntry;
import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.enums.Sentiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Service
public class JournalSearchService {

    @Autowired
    private UserService userService;

    /**
     * Searches only the entries embedded in the authenticated user's document.
     * All filters are optional, date bounds are inclusive, and descending date
     * order is used when no sort direction is supplied.
     */
    public List<JournalEntry> searchJournalEntries(String username, String title, Sentiment sentiment,
                                                    LocalDate startDate, LocalDate endDate, String sort) {
        User user = getUser(username);
        validateDateRange(startDate, endDate);
        List<JournalEntry> filteredEntries = filterEntries(
                getJournalEntries(user), normalizeTitle(title), sentiment, startDate, endDate
        );

        sortEntriesByDate(filteredEntries, resolveSortDirection(sort));
        return filteredEntries;
    }

    private User getUser(String username) {
        User user = userService.findByUserName(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }
        return user;
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }

    private List<JournalEntry> getJournalEntries(User user) {
        return user.getJournalEntries() == null ? Collections.emptyList() : user.getJournalEntries();
    }

    private String normalizeTitle(String title) {
        return title == null ? "" : title.trim().toLowerCase(Locale.ROOT);
    }

    private List<JournalEntry> filterEntries(List<JournalEntry> entries, String title, Sentiment sentiment,
                                              LocalDate startDate, LocalDate endDate) {
        List<JournalEntry> filteredEntries = new ArrayList<>();

        for (JournalEntry entry : entries) {
            if (matchesFilters(entry, title, sentiment, startDate, endDate)) {
                filteredEntries.add(entry);
            }
        }

        return filteredEntries;
    }

    private boolean matchesFilters(JournalEntry entry, String title, Sentiment sentiment,
                                   LocalDate startDate, LocalDate endDate) {
        return entry != null
                && matchesTitle(entry, title)
                && matchesSentiment(entry, sentiment)
                && matchesDateRange(entry, startDate, endDate);
    }

    private boolean matchesTitle(JournalEntry entry, String title) {
        return title.isEmpty()
                || (entry.getTitle() != null && entry.getTitle().toLowerCase(Locale.ROOT).contains(title));
    }

    private boolean matchesSentiment(JournalEntry entry, Sentiment sentiment) {
        return sentiment == null || entry.getSentiment() == sentiment;
    }

    private boolean matchesDateRange(JournalEntry entry, LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return true;
        }
        if (entry.getDate() == null) {
            return false;
        }

        LocalDate entryDate = entry.getDate().toLocalDate();
        return (startDate == null || !entryDate.isBefore(startDate))
                && (endDate == null || !entryDate.isAfter(endDate));
    }

    private String resolveSortDirection(String sort) {
        String sortDirection = sort == null || sort.trim().isEmpty() ? "desc" : sort.trim();
        if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("Sort must be either asc or desc");
        }
        return sortDirection;
    }

    private void sortEntriesByDate(List<JournalEntry> entries, String sortDirection) {
        entries.sort((first, second) -> compareDates(first.getDate(), second.getDate(), sortDirection));
    }

    private int compareDates(LocalDateTime firstDate, LocalDateTime secondDate, String sortDirection) {
        if (firstDate == null && secondDate == null) {
            return 0;
        }
        if (firstDate == null) {
            return 1;
        }
        if (secondDate == null) {
            return -1;
        }

        return sortDirection.equalsIgnoreCase("asc")
                ? firstDate.compareTo(secondDate)
                : secondDate.compareTo(firstDate);
    }
}

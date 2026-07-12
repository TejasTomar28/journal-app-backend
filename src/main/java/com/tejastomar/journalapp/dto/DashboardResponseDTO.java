package com.tejastomar.journalapp.dto;

import com.tejastomar.journalapp.enums.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDTO {
    private String username;

    private int currentStreak;

    private int totalEntries;

    private int entriesThisWeek;

    private int entriesThisMonth;

    private Sentiment dominantSentiment;

    private LocalDate lastEntryDate;
}

package com.tejastomar.journalapp.dto;

import com.tejastomar.journalapp.enums.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyReflectionEmailDTO {
    private String username;
    private String email;
    private int currentStreak;
    private int entriesThisWeek;
    private double journalConsistency;
    private Sentiment dominantSentiment;
    private double averageEntriesPerDay;
    private String weeklySummary;
    private String positiveObservation;
    private String reflectionQuestion;
    private String encouragement;
}
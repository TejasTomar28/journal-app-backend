package com.tejastomar.journalapp.dto;

import com.tejastomar.journalapp.enums.Sentiment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIInsightResponseDTO {
    private String summary;
    private Sentiment detectedSentiment;
    private String positiveObservation;
    private String reflectionQuestion;
    private String encouragement;
    private LocalDateTime generatedAt;
}

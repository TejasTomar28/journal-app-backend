package com.tejastomar.journalapp.entity;

import com.tejastomar.journalapp.enums.Sentiment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "ai_insights")
@Data
@NoArgsConstructor
public class AIInsight {

    @Id
    private ObjectId id;
    private ObjectId journalEntryId;
    private String summary;
    private Sentiment detectedSentiment;
    private String positiveObservation;
    private String reflectionQuestion;
    private String encouragement;
    private String modelUsed;
    private LocalDateTime generatedAt;
    private boolean generated;
    private String username;
}

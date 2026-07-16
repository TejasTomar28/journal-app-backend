package com.tejastomar.journalapp.entity;

import com.tejastomar.journalapp.enums.ReflectionCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reflection_prompts")
@Data
@NoArgsConstructor
public class ReflectionPrompt {

    @Id
    private ObjectId id;
    private String prompt;
    private ReflectionCategory category;
    private boolean active;
    private LocalDateTime createdAt;
}

package com.tejastomar.journalapp.dto;

import com.tejastomar.journalapp.enums.ReflectionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReflectionPromptResponseDTO {
    private String id;
    private String prompt;
    private ReflectionCategory category;
}

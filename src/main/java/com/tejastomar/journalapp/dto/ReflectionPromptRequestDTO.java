package com.tejastomar.journalapp.dto;

import com.tejastomar.journalapp.enums.ReflectionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReflectionPromptRequestDTO {

    @NotBlank
    @Size(min = 10, max = 300)
    private String prompt;

    @NotNull
    private ReflectionCategory category;

    private boolean active;
}

package com.tejastomar.journalapp.dto;

import com.tejastomar.journalapp.enums.ReflectionCategory;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReflectionPromptRequestDTOTests {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validation_rejectsBlankPrompt() {
        ReflectionPromptRequestDTO request = new ReflectionPromptRequestDTO(
                " ", ReflectionCategory.GRATITUDE, true
        );

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    void validation_acceptsValidPromptRequest() {
        ReflectionPromptRequestDTO request = new ReflectionPromptRequestDTO(
                "What are you grateful for today?", ReflectionCategory.GRATITUDE, true
        );

        assertTrue(validator.validate(request).isEmpty());
    }
}

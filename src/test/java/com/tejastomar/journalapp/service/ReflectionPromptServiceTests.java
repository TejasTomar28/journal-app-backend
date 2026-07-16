package com.tejastomar.journalapp.service;

import com.tejastomar.journalapp.dto.ReflectionPromptRequestDTO;
import com.tejastomar.journalapp.dto.ReflectionPromptResponseDTO;
import com.tejastomar.journalapp.entity.ReflectionPrompt;
import com.tejastomar.journalapp.enums.ReflectionCategory;
import com.tejastomar.journalapp.repository.ReflectionPromptRepository;
import com.tejastomar.journalapp.services.ReflectionPromptService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReflectionPromptServiceTests {

    @Mock
    private ReflectionPromptRepository reflectionPromptRepository;

    @InjectMocks
    private ReflectionPromptService reflectionPromptService;

    @Test
    void getTodaysPrompt_throwsWhenNoActivePromptsExist() {
        when(reflectionPromptRepository.findByActiveTrue()).thenReturn(List.of());

        assertThrows(IllegalStateException.class, reflectionPromptService::getTodaysPrompt);
    }

    @Test
    void getTodaysPrompt_returnsTheSamePromptForRepeatedCallsOnTheSameDay() {
        ReflectionPrompt firstPrompt = prompt("What are you grateful for?", ReflectionCategory.GRATITUDE, true);
        ReflectionPrompt secondPrompt = prompt("What did you learn today?", ReflectionCategory.SELF_REFLECTION, true);
        when(reflectionPromptRepository.findByActiveTrue()).thenReturn(List.of(firstPrompt, secondPrompt));

        ReflectionPromptResponseDTO firstResponse = reflectionPromptService.getTodaysPrompt();
        ReflectionPromptResponseDTO secondResponse = reflectionPromptService.getTodaysPrompt();

        assertTrue(Set.of(firstPrompt.getId().toHexString(), secondPrompt.getId().toHexString()).contains(firstResponse.getId()));
        assertEquals(firstResponse.getId(), secondResponse.getId());
    }

    @Test
    void getTodaysPrompt_usesTheCurrentActivePromptList() {
        ReflectionPrompt firstPrompt = prompt("What are you grateful for?", ReflectionCategory.GRATITUDE, true);
        ReflectionPrompt replacementPrompt = prompt("What did you learn today?", ReflectionCategory.SELF_REFLECTION, true);
        when(reflectionPromptRepository.findByActiveTrue())
                .thenReturn(List.of(firstPrompt))
                .thenReturn(List.of(replacementPrompt));

        ReflectionPromptResponseDTO firstResponse = reflectionPromptService.getTodaysPrompt();
        ReflectionPromptResponseDTO replacementResponse = reflectionPromptService.getTodaysPrompt();

        assertEquals(firstPrompt.getId().toHexString(), firstResponse.getId());
        assertEquals(replacementPrompt.getId().toHexString(), replacementResponse.getId());
    }

    @Test
    void createPrompt_setsCreatedAtAndReturnsPublicResponse() {
        ReflectionPromptRequestDTO request = request("What gave you energy today?", ReflectionCategory.MINDFULNESS, true);
        when(reflectionPromptRepository.save(any(ReflectionPrompt.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ReflectionPromptResponseDTO response = reflectionPromptService.createPrompt(request);

        assertEquals("What gave you energy today?", response.getPrompt());
        assertEquals(ReflectionCategory.MINDFULNESS, response.getCategory());
        verify(reflectionPromptRepository).save(any(ReflectionPrompt.class));
    }

    @Test
    void updatePrompt_updatesPromptCategoryAndActiveStatus() {
        ObjectId id = new ObjectId();
        ReflectionPrompt existingPrompt = prompt("Old prompt", ReflectionCategory.CAREER, true);
        existingPrompt.setId(id);
        ReflectionPromptRequestDTO updatedPrompt = request("New prompt", ReflectionCategory.EMOTIONS, false);
        when(reflectionPromptRepository.findById(id)).thenReturn(java.util.Optional.of(existingPrompt));
        when(reflectionPromptRepository.save(existingPrompt)).thenReturn(existingPrompt);

        ReflectionPromptResponseDTO response = reflectionPromptService.updatePrompt(id, updatedPrompt);

        assertEquals("New prompt", existingPrompt.getPrompt());
        assertEquals(ReflectionCategory.EMOTIONS, existingPrompt.getCategory());
        assertTrue(!existingPrompt.isActive());
        assertEquals("New prompt", response.getPrompt());
    }

    @Test
    void deletePrompt_marksExistingPromptAsInactive() {
        ObjectId id = new ObjectId();
        ReflectionPrompt prompt = prompt("A prompt for reflection", ReflectionCategory.EMOTIONS, true);
        prompt.setId(id);
        when(reflectionPromptRepository.findById(id)).thenReturn(java.util.Optional.of(prompt));

        assertTrue(reflectionPromptService.deletePrompt(id));

        assertTrue(!prompt.isActive());
        verify(reflectionPromptRepository).save(prompt);
        verify(reflectionPromptRepository, never()).deleteById(id);
    }

    private ReflectionPrompt prompt(String text, ReflectionCategory category, boolean active) {
        ReflectionPrompt prompt = new ReflectionPrompt();
        prompt.setId(new ObjectId());
        prompt.setPrompt(text);
        prompt.setCategory(category);
        prompt.setActive(active);
        return prompt;
    }

    private ReflectionPromptRequestDTO request(String text, ReflectionCategory category, boolean active) {
        return new ReflectionPromptRequestDTO(text, category, active);
    }
}

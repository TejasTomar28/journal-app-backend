package com.tejastomar.journalapp.services;

import com.tejastomar.journalapp.dto.ReflectionPromptRequestDTO;
import com.tejastomar.journalapp.dto.ReflectionPromptResponseDTO;
import com.tejastomar.journalapp.entity.ReflectionPrompt;
import com.tejastomar.journalapp.repository.ReflectionPromptRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReflectionPromptService {

    @Autowired
    private ReflectionPromptRepository reflectionPromptRepository;

    public ReflectionPromptResponseDTO getTodaysPrompt() {
        return toResponseDTO(selectTodaysPrompt(getActivePrompts()));
    }

    public ReflectionPromptResponseDTO createPrompt(ReflectionPromptRequestDTO request) {
        ReflectionPrompt prompt = new ReflectionPrompt();
        applyRequestValues(prompt, request);
        prompt.setCreatedAt(LocalDateTime.now());
        return toResponseDTO(reflectionPromptRepository.save(prompt));
    }

    public List<ReflectionPromptResponseDTO> getAllPrompts() {
        return reflectionPromptRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ReflectionPromptResponseDTO updatePrompt(ObjectId id, ReflectionPromptRequestDTO request) {
        ReflectionPrompt existingPrompt = reflectionPromptRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reflection prompt not found"));

        applyRequestValues(existingPrompt, request);
        return toResponseDTO(reflectionPromptRepository.save(existingPrompt));
    }

    public boolean deletePrompt(ObjectId id) {
        ReflectionPrompt prompt = reflectionPromptRepository.findById(id).orElse(null);
        if (prompt == null) {
            return false;
        }

        prompt.setActive(false);
        reflectionPromptRepository.save(prompt);
        return true;
    }

    private List<ReflectionPrompt> getActivePrompts() {
        List<ReflectionPrompt> activePrompts = new ArrayList<>(reflectionPromptRepository.findByActiveTrue());
        if (activePrompts.isEmpty()) {
            throw new IllegalStateException("No active reflection prompts available");
        }

        activePrompts.sort(Comparator.comparing(prompt -> prompt.getId().toHexString()));
        return activePrompts;
    }

    private ReflectionPrompt selectTodaysPrompt(List<ReflectionPrompt> activePrompts) {
        int index = Math.floorMod(LocalDate.now().toEpochDay(), activePrompts.size());
        return activePrompts.get(index);
    }

    private void applyRequestValues(ReflectionPrompt prompt, ReflectionPromptRequestDTO request) {
        prompt.setPrompt(request.getPrompt());
        prompt.setCategory(request.getCategory());
        prompt.setActive(request.isActive());
    }

    private ReflectionPromptResponseDTO toResponseDTO(ReflectionPrompt prompt) {
        return ReflectionPromptResponseDTO.builder()
                .id(prompt.getId() == null ? null : prompt.getId().toHexString())
                .prompt(prompt.getPrompt())
                .category(prompt.getCategory())
                .build();
    }
}

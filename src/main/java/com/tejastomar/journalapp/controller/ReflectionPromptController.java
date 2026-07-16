package com.tejastomar.journalapp.controller;

import com.tejastomar.journalapp.dto.ReflectionPromptRequestDTO;
import com.tejastomar.journalapp.dto.ReflectionPromptResponseDTO;
import com.tejastomar.journalapp.services.ReflectionPromptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/reflection")
@Tag(name = "Reflection Prompt APIs")
public class ReflectionPromptController {

    @Autowired
    private ReflectionPromptService reflectionPromptService;

    @GetMapping("/today")
    @Operation(
            summary = "Get Today's Reflection Prompt",
            description = "Returns the active reflection prompt selected for the current day."
    )
    public ResponseEntity<ReflectionPromptResponseDTO> getTodaysPrompt() {
        return ResponseEntity.ok(reflectionPromptService.getTodaysPrompt());
    }

    @PostMapping
    @Operation(
            summary = "Create Reflection Prompt",
            description = "Creates a new reflection prompt. Accessible only by ADMIN."
    )
    public ResponseEntity<ReflectionPromptResponseDTO> createPrompt(
            @Valid @RequestBody ReflectionPromptRequestDTO prompt) {
        ReflectionPromptResponseDTO createdPrompt = reflectionPromptService.createPrompt(prompt);
        return new ResponseEntity<>(createdPrompt, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
            summary = "Get All Reflection Prompts",
            description = "Returns every reflection prompt. Accessible only by ADMIN."
    )
    public ResponseEntity<List<ReflectionPromptResponseDTO>> getAllPrompts() {
        return ResponseEntity.ok(reflectionPromptService.getAllPrompts());
    }

    @PutMapping("/id/{id}")
    @Operation(
            summary = "Update Reflection Prompt",
            description = "Updates prompt text, category, and active status. Accessible only by ADMIN."
    )
    public ResponseEntity<ReflectionPromptResponseDTO> updatePrompt(
            @PathVariable ObjectId id, @Valid @RequestBody ReflectionPromptRequestDTO prompt) {
        try {
            return ResponseEntity.ok(reflectionPromptService.updatePrompt(id, prompt));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/id/{id}")
    @Operation(
            summary = "Delete Reflection Prompt",
            description = "Deletes a reflection prompt. Accessible only by ADMIN."
    )
    public ResponseEntity<Void> deletePrompt(@PathVariable ObjectId id) {
        if (reflectionPromptService.deletePrompt(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

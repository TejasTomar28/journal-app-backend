package com.tejastomar.journalapp.controller;

import com.tejastomar.journalapp.dto.AIInsightResponseDTO;
import com.tejastomar.journalapp.services.AIInsightService;
import com.tejastomar.journalapp.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@Tag(name = "AI Insight APIs")
public class AIInsightController {

    @Autowired
    private AIInsightService aiInsightService;

    @PostMapping("/generate/{journalId}")
    @Operation(
            summary = "Generate Journal AI Insight",
            description = "Generates one stored reflection insight for an AI-enabled journal entry owned by the authenticated user."
    )
    public ResponseEntity<AIInsightResponseDTO> generateInsight(@PathVariable ObjectId journalId) {
        return ResponseEntity.ok(aiInsightService.generateInsight(SecurityUtil.getCurrentUsername(), journalId));
    }

    @GetMapping("/{journalId}")
    @Operation(
            summary = "Get Stored Journal AI Insight",
            description = "Returns the stored AI insight for a journal entry owned by the authenticated user."
    )
    public ResponseEntity<AIInsightResponseDTO> getInsight(@PathVariable ObjectId journalId) {
        return ResponseEntity.ok(aiInsightService.getStoredInsight(SecurityUtil.getCurrentUsername(), journalId));
    }
}

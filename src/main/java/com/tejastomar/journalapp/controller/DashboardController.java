package com.tejastomar.journalapp.controller;

import com.tejastomar.journalapp.dto.DashboardResponseDTO;
import com.tejastomar.journalapp.services.DashboardService;
import com.tejastomar.journalapp.utils.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard APIs")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    @Operation(
            summary = "Dashboard",
            description = "Returns dashboard information for the authenticated user."
    )
    public ResponseEntity<DashboardResponseDTO> getDashboard() {
        DashboardResponseDTO response =
                dashboardService.getDashboard(SecurityUtil.getCurrentUsername());

        return ResponseEntity.ok(response);
    }
}

package com.example.backend.rest;

import com.example.backend.service.MatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    @PostMapping
    public ResponseEntity<String> saveMatchingSettings(@RequestBody Map<String, Object> request) {
        System.out.println(request);
        System.out.println(request);
        MatchingService.setMinMatchingScore((Integer) request.get("minScoreValue"));
        MatchingService.setPreciseMatching((Boolean) request.get("preciseMatching"));

        return ResponseEntity.ok("Successfully updated matching settings.");
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getMatchingSettings() {
        Map<String, Object> response = new HashMap<>();
        response.put("minMatchingScore", MatchingService.getMinMatchingScore());
        response.put("preciseMatching", MatchingService.isPreciseMatching());
        return ResponseEntity.ok(response);
    }
}

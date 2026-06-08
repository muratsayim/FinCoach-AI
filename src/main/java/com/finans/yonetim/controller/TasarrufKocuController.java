package com.finans.yonetim.controller;

import com.finans.yonetim.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tasarruf-kocu")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TasarrufKocuController {

    private final GeminiService geminiService;

    @Autowired
    public TasarrufKocuController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/tavsiye")
    public ResponseEntity<?> tavsiyeAl(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @RequestHeader(value = "X-Gemini-API-Key", required = false) String customApiKey,
            @RequestHeader(value = "X-AI-Mode-Enabled", required = false, defaultValue = "false") String aiModeEnabled) {
        
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Oturum açmanız gerekiyor."));
        }
        
        try {
            String tavsiye = geminiService.tasarrufTavsiyesiUret(userId, customApiKey, Boolean.parseBoolean(aiModeEnabled));
            return ResponseEntity.ok(Map.of("tavsiye", tavsiye));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}

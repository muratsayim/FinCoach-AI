package com.finans.yonetim.controller;

import com.finans.yonetim.model.Islem;
import com.finans.yonetim.service.IslemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/islemler")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IslemController {

    private final IslemService islemService;

    @Autowired
    public IslemController(IslemService islemService) {
        this.islemService = islemService;
    }

    @PostMapping
    public ResponseEntity<?> kaydet(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @RequestBody Islem islem) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Oturum açmanız gerekiyor."));
        }
        try {
            Islem kaydedilenIslem = islemService.kaydet(islem, userId);
            return new ResponseEntity<>(kaydedilenIslem, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> tumunuGetir(
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Oturum açmanız gerekiyor."));
        }
        try {
            List<Islem> islemler = islemService.tumunuGetir(userId);
            return ResponseEntity.ok(islemler);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @PathVariable Long id) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Oturum açmanız gerekiyor."));
        }
        try {
            Islem islem = islemService.getById(id, userId);
            return ResponseEntity.ok(islem);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> guncelle(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @PathVariable Long id,
            @RequestBody Islem yeniIslem) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Oturum açmanız gerekiyor."));
        }
        try {
            Islem guncellenenIslem = islemService.guncelle(id, yeniIslem, userId);
            return ResponseEntity.ok(guncellenenIslem);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> sil(
            @RequestHeader(value = "X-User-Id", required = false) Long userId,
            @PathVariable Long id) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Oturum açmanız gerekiyor."));
        }
        try {
            islemService.sil(id, userId);
            Map<String, String> response = Map.of("message", "İşlem başarıyla silindi. ID: " + id);
            return ResponseEntity.ok(response);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/analiz")
    public ResponseEntity<?> analizGetir(
            @RequestHeader(value = "X-User-Id", required = false) Long userId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Oturum açmanız gerekiyor."));
        }
        try {
            Map<String, Object> analiz = islemService.analizGetir(userId);
            return ResponseEntity.ok(analiz);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}

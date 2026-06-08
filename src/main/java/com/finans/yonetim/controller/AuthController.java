package com.finans.yonetim.controller;

import com.finans.yonetim.model.Kullanici;
import com.finans.yonetim.service.KullaniciService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final KullaniciService kullaniciService;

    @Autowired
    public AuthController(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            String adSoyad = request.get("adSoyad");
            String email = request.get("email");
            String sifre = request.get("sifre");

            Kullanici kullanici = kullaniciService.kayitOl(adSoyad, email, sifre);
            return new ResponseEntity<>(Map.of(
                    "id", kullanici.getId(),
                    "adSoyad", kullanici.getAdSoyad(),
                    "email", kullanici.getEmail(),
                    "message", "Kayıt başarıyla tamamlandı!"
            ), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Kayıt sırasında beklenmedik bir hata oluştu."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String sifre = request.get("sifre");

            Kullanici kullanici = kullaniciService.girisYap(email, sifre);
            return ResponseEntity.ok(Map.of(
                    "id", kullanici.getId(),
                    "adSoyad", kullanici.getAdSoyad(),
                    "email", kullanici.getEmail(),
                    "message", "Giriş başarılı!"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Giriş sırasında beklenmedik bir hata oluştu."));
        }
    }
}

package com.finans.yonetim.service;

import com.finans.yonetim.model.Kullanici;
import com.finans.yonetim.repository.KullaniciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

@Service
public class KullaniciServiceImpl implements KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    @Autowired
    public KullaniciServiceImpl(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    @Override
    public Kullanici kayitOl(String adSoyad, String email, String sifre) {
        if (adSoyad == null || adSoyad.trim().isEmpty()) {
            throw new IllegalArgumentException("Ad Soyad alanı boş bırakılamaz.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("E-posta alanı boş bırakılamaz.");
        }
        if (sifre == null || sifre.trim().isEmpty()) {
            throw new IllegalArgumentException("Şifre alanı boş bırakılamaz.");
        }

        String normalizedEmail = email.trim().toLowerCase();
        if (kullaniciRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Bu e-posta adresiyle zaten kayıtlı bir hesap bulunmaktadır.");
        }

        Kullanici yeniKullanici = new Kullanici();
        yeniKullanici.setAdSoyad(adSoyad.trim());
        yeniKullanici.setEmail(normalizedEmail);
        yeniKullanici.setSifre(hashPassword(sifre));

        return kullaniciRepository.save(yeniKullanici);
    }

    @Override
    public Kullanici girisYap(String email, String sifre) {
        if (email == null || email.trim().isEmpty() || sifre == null || sifre.trim().isEmpty()) {
            throw new IllegalArgumentException("E-posta ve şifre alanları boş bırakılamaz.");
        }

        String normalizedEmail = email.trim().toLowerCase();
        Kullanici kullanici = kullaniciRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new IllegalArgumentException("E-posta adresi veya şifre hatalı."));

        String hashedInput = hashPassword(sifre);
        if (!kullanici.getSifre().equals(hashedInput)) {
            throw new IllegalArgumentException("E-posta adresi veya şifre hatalı.");
        }

        return kullanici;
    }

    @Override
    public Kullanici getById(Long id) {
        return kullaniciRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Kullanıcı bulunamadı. ID: " + id));
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 hashing algoritması yüklenemedi.", e);
        }
    }
}

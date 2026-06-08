package com.finans.yonetim.service;

import com.finans.yonetim.model.Kullanici;

public interface KullaniciService {
    Kullanici kayitOl(String adSoyad, String email, String sifre);
    Kullanici girisYap(String email, String sifre);
    Kullanici getById(Long id);
}

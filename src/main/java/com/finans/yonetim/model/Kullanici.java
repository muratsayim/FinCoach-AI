package com.finans.yonetim.model;

import jakarta.persistence.*;

@Entity
@Table(name = "kullanicilar", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String adSoyad;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String sifre; // SHA-256 ile hash'lenmiş şifre

    // Constructors
    public Kullanici() {
    }

    public Kullanici(Long id, String adSoyad, String email, String sifre) {
        this.id = id;
        this.adSoyad = adSoyad;
        this.email = email;
        this.sifre = sifre;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
}

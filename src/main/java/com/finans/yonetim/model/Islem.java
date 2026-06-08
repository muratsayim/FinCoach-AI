package com.finans.yonetim.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "islemler")
public class Islem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String aciklama;

    @Column(nullable = false)
    private Double tutar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IslemTur tur;

    @Column(nullable = false)
    private String kategori;

    @Column(nullable = false)
    private LocalDate tarih;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Kullanici kullanici;

    // Constructors
    public Islem() {
    }

    public Islem(Long id, String aciklama, Double tutar, IslemTur tur, String kategori, LocalDate tarih) {
        this.id = id;
        this.aciklama = aciklama;
        this.tutar = tutar;
        this.tur = tur;
        this.kategori = kategori;
        this.tarih = tarih;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Double getTutar() {
        return tutar;
    }

    public void setTutar(Double tutar) {
        this.tutar = tutar;
    }

    public IslemTur getTur() {
        return tur;
    }

    public void setTur(IslemTur tur) {
        this.tur = tur;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public LocalDate getTarih() {
        return tarih;
    }

    public void setTarih(LocalDate tarih) {
        this.tarih = tarih;
    }

    public Kullanici getKullanici() {
        return kullanici;
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
    }
}

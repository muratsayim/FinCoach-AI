package com.finans.yonetim;

import com.finans.yonetim.model.Islem;
import com.finans.yonetim.model.IslemTur;
import com.finans.yonetim.model.Kullanici;
import com.finans.yonetim.repository.IslemRepository;
import com.finans.yonetim.repository.KullaniciRepository;
import com.finans.yonetim.service.IslemService;
import com.finans.yonetim.service.KullaniciService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class FinansYonetimApplicationTests {

    @Autowired
    private IslemService islemService;

    @Autowired
    private IslemRepository islemRepository;

    @Autowired
    private KullaniciService kullaniciService;

    @Autowired
    private KullaniciRepository kullaniciRepository;

    private Long testUserId;

    @BeforeEach
    void setUp() {
        islemRepository.deleteAll();
        kullaniciRepository.deleteAll();
        Kullanici user = kullaniciService.kayitOl("Test User", "test@example.com", "password");
        testUserId = user.getId();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testIslemKaydetVeGetir() {
        Islem islem = new Islem(null, "Aylık Maaş", 15000.0, IslemTur.GELIR, "Maaş", LocalDate.now());
        Islem kaydedilen = islemService.kaydet(islem, testUserId);

        assertNotNull(kaydedilen.getId());
        assertEquals("Aylık Maaş", kaydedilen.getAciklama());
        assertEquals(15000.0, kaydedilen.getTutar());
        assertEquals(IslemTur.GELIR, kaydedilen.getTur());

        List<Islem> tumIslemler = islemService.tumunuGetir(testUserId);
        assertEquals(1, tumIslemler.size());
    }

    @Test
    void testFinansalAnaliz() {
        Islem gelir = new Islem(null, "Maaş Ödemesi", 20000.0, IslemTur.GELIR, "Maaş", LocalDate.now());
        Islem gider1 = new Islem(null, "Market Alışverişi", 2000.0, IslemTur.GIDER, "Yemek", LocalDate.now());
        Islem gider2 = new Islem(null, "Abonelikler", 500.0, IslemTur.GIDER, "Eğlence", LocalDate.now());

        islemService.kaydet(gelir, testUserId);
        islemService.kaydet(gider1, testUserId);
        islemService.kaydet(gider2, testUserId);

        Map<String, Object> analiz = islemService.analizGetir(testUserId);

        assertEquals(20000.0, analiz.get("toplamGelir"));
        assertEquals(2500.0, analiz.get("toplamGider"));
        assertEquals(17500.0, analiz.get("netBakiye"));
        assertEquals(87.5, analiz.get("tasarrufOrani"));

        @SuppressWarnings("unchecked")
        Map<String, Double> kategoriGiderleri = (Map<String, Double>) analiz.get("kategoriGiderleri");
        assertEquals(2000.0, kategoriGiderleri.get("Yemek"));
        assertEquals(500.0, kategoriGiderleri.get("Eğlence"));
    }
}

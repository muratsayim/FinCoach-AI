package com.finans.yonetim.service;

import com.finans.yonetim.model.Islem;
import com.finans.yonetim.model.IslemTur;
import com.finans.yonetim.model.Kullanici;
import com.finans.yonetim.repository.IslemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class IslemServiceImpl implements IslemService {

    private final IslemRepository islemRepository;
    private final KullaniciService kullaniciService;

    @Autowired
    public IslemServiceImpl(IslemRepository islemRepository, KullaniciService kullaniciService) {
        this.islemRepository = islemRepository;
        this.kullaniciService = kullaniciService;
    }

    @Override
    public Islem kaydet(Islem islem, Long kullaniciId) {
        Kullanici kullanici = kullaniciService.getById(kullaniciId);
        islem.setKullanici(kullanici);
        if (islem.getTarih() == null) {
            islem.setTarih(java.time.LocalDate.now());
        }
        return islemRepository.save(islem);
    }

    @Override
    public List<Islem> tumunuGetir(Long kullaniciId) {
        return islemRepository.findAllByKullaniciIdOrderByTarihDesc(kullaniciId);
    }

    @Override
    public Islem getById(Long id, Long kullaniciId) {
        Islem islem = islemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("İşlem bulunamadı. ID: " + id));
        if (!islem.getKullanici().getId().equals(kullaniciId)) {
            throw new SecurityException("Bu işlem kaydına erişim yetkiniz bulunmamaktadır.");
        }
        return islem;
    }

    @Override
    public Islem guncelle(Long id, Islem yeniIslem, Long kullaniciId) {
        Islem mevcutIslem = getById(id, kullaniciId);
        mevcutIslem.setAciklama(yeniIslem.getAciklama());
        mevcutIslem.setTutar(yeniIslem.getTutar());
        mevcutIslem.setTur(yeniIslem.getTur());
        mevcutIslem.setKategori(yeniIslem.getKategori());
        if (yeniIslem.getTarih() != null) {
            mevcutIslem.setTarih(yeniIslem.getTarih());
        }
        return islemRepository.save(mevcutIslem);
    }

    @Override
    public void sil(Long id, Long kullaniciId) {
        Islem islem = getById(id, kullaniciId);
        islemRepository.delete(islem);
    }

    @Override
    public Map<String, Object> analizGetir(Long kullaniciId) {
        List<Islem> tumIslemler = islemRepository.findAllByKullaniciId(kullaniciId);

        double toplamGelir = 0.0;
        double toplamGider = 0.0;

        Map<String, Double> kategoriGiderleri = new HashMap<>();
        Map<String, Double> kategoriGelirleri = new HashMap<>();
        
        // Aylık veriler için ağaç yapısı (Tarih sırasına göre sıralı olması için TreeMap)
        Map<String, Map<String, Double>> aylikVeriler = new TreeMap<>();

        DateTimeFormatter ayFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (Islem islem : tumIslemler) {
            double tutar = islem.getTutar();
            String kategori = islem.getKategori();
            String ay = islem.getTarih().format(ayFormatter);

            // Aylık harita başlatma
            aylikVeriler.putIfAbsent(ay, new HashMap<>(Map.of("gelir", 0.0, "gider", 0.0)));

            if (islem.getTur() == IslemTur.GELIR) {
                toplamGelir += tutar;
                kategoriGelirleri.put(kategori, kategoriGelirleri.getOrDefault(kategori, 0.0) + tutar);
                
                Map<String, Double> ayMap = aylikVeriler.get(ay);
                ayMap.put("gelir", ayMap.get("gelir") + tutar);
            } else {
                toplamGider += tutar;
                kategoriGiderleri.put(kategori, kategoriGiderleri.getOrDefault(kategori, 0.0) + tutar);
                
                Map<String, Double> ayMap = aylikVeriler.get(ay);
                ayMap.put("gider", ayMap.get("gider") + tutar);
            }
        }

        double netBakiye = toplamGelir - toplamGider;
        double tasarrufOrani = 0.0;
        if (toplamGelir > 0) {
            tasarrufOrani = (netBakiye / toplamGelir) * 100.0;
        }

        Map<String, Object> analiz = new HashMap<>();
        analiz.put("toplamGelir", toplamGelir);
        analiz.put("toplamGider", toplamGider);
        analiz.put("netBakiye", netBakiye);
        analiz.put("tasarrufOrani", tasarrufOrani);
        analiz.put("kategoriGiderleri", kategoriGiderleri);
        analiz.put("kategoriGelirleri", kategoriGelirleri);
        analiz.put("aylikVeriler", aylikVeriler);

        return analiz;
    }
}

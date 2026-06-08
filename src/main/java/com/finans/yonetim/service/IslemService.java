package com.finans.yonetim.service;

import com.finans.yonetim.model.Islem;
import java.util.List;
import java.util.Map;

public interface IslemService {
    Islem kaydet(Islem islem, Long kullaniciId);
    List<Islem> tumunuGetir(Long kullaniciId);
    Islem getById(Long id, Long kullaniciId);
    Islem guncelle(Long id, Islem yeniIslem, Long kullaniciId);
    void sil(Long id, Long kullaniciId);
    Map<String, Object> analizGetir(Long kullaniciId);
}

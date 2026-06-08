package com.finans.yonetim.repository;

import com.finans.yonetim.model.Islem;
import com.finans.yonetim.model.IslemTur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IslemRepository extends JpaRepository<Islem, Long> {
    List<Islem> findByKullaniciIdAndTur(Long kullaniciId, IslemTur tur);
    List<Islem> findByKullaniciIdAndKategori(Long kullaniciId, String kategori);
    List<Islem> findAllByKullaniciIdOrderByTarihDesc(Long kullaniciId);
    List<Islem> findAllByKullaniciId(Long kullaniciId);
}

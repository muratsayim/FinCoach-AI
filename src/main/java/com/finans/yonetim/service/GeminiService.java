package com.finans.yonetim.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finans.yonetim.model.Islem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key:}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final IslemService islemService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public GeminiService(IslemService islemService) {
        this.islemService = islemService;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String tasarrufTavsiyesiUret(Long kullaniciId) {
        return tasarrufTavsiyesiUret(kullaniciId, null, false);
    }

    public String tasarrufTavsiyesiUret(Long kullaniciId, String customApiKey) {
        boolean aiMode = customApiKey != null && !customApiKey.trim().isEmpty();
        return tasarrufTavsiyesiUret(kullaniciId, customApiKey, aiMode);
    }

    public String tasarrufTavsiyesiUret(Long kullaniciId, String customApiKey, boolean aiModeEnabled) {
        Map<String, Object> analiz = islemService.analizGetir(kullaniciId);
        List<Islem> tumIslemler = islemService.tumunuGetir(kullaniciId);

        double toplamGelir = (double) analiz.get("toplamGelir");
        double toplamGider = (double) analiz.get("toplamGider");
        double netBakiye = (double) analiz.get("netBakiye");
        double tasarrufOrani = (double) analiz.get("tasarrufOrani");
        Map<?, ?> kategoriGiderleri = (Map<?, ?>) analiz.get("kategoriGiderleri");

        if (tumIslemler.isEmpty()) {
            return "Sistemde henüz finansal işlem kaydı bulunmuyor. Gelir ve giderlerinizi ekledikten sonra size özel yapay zeka tasarruf önerileri üretebilirim!";
        }

        if (!aiModeEnabled) {
            return getYedekTasarrufTavsiyesi(toplamGelir, toplamGider, netBakiye, tasarrufOrani, kategoriGiderleri);
        }

        // Prompt hazırlama
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Sen profesyonel bir Kişisel Finans Danışmanı ve Tasarruf Koçusun. ")
                .append("Aşağıdaki kullanıcının finansal durum verilerini analiz et ve tamamen Türkçe, motive edici, samimi ve son derece somut/aksiyon alınabilir öneriler sun.\n\n")
                .append("### Finansal Durum:\n")
                .append("- Toplam Gelir: ").append(String.format("%,.2f TL\n", toplamGelir))
                .append("- Toplam Gider: ").append(String.format("%,.2f TL\n", toplamGider))
                .append("- Net Bakiye: ").append(String.format("%,.2f TL\n", netBakiye))
                .append("- Tasarruf Oranı: %").append(String.format("%.1f\n\n", tasarrufOrani));

        promptBuilder.append("### Kategori Bazlı Harcama Dağılımı:\n");
        kategoriGiderleri.forEach((kategori, tutar) -> {
            promptBuilder.append("- ").append(kategori).append(": ").append(String.format("%,.2f TL\n", tutar));
        });

        promptBuilder.append("\n### Son 5 Finansal İşlem:\n");
        int count = 0;
        for (Islem islem : tumIslemler) {
            if (count >= 5) break;
            promptBuilder.append("- ").append(islem.getTarih()).append(" | ")
                    .append(islem.getAciklama()).append(" (").append(islem.getKategori()).append("): ")
                    .append(islem.getTur()).append(" ").append(String.format("%,.2f TL\n", islem.getTutar()));
            count++;
        }

        promptBuilder.append("\nLütfen analizinde şu başlıkları içerecek şekilde zengin markdown formatında yanıt ver:\n")
                .append("1. **Finansal Durum Özeti**: Genel durumun değerlendirilmesi ve tasarruf oranının yorumlanması.\n")
                .append("2. **Dikkat Çeken Harcamalar ve Fırsatlar**: En çok harcanan kategoriler ve buralardaki potansiyel israflar.\n")
                .append("3. **Alternatif Senaryo Analizi**: Kullanıcı için en az iki farklı 'şunu %X oranında azaltırsanız, ayda Y TL tasarruf edersiniz ve bu para ile Z yapabilirsiniz' şeklinde somut senaryo.\n")
                .append("4. **Tasarruf Koçu Eylem Planı**: Borçları azaltmak ve finansal farkındalığı artırmak için bu hafta atılabilecek 3 basit adım.");

        String activeKey = (customApiKey != null && !customApiKey.trim().isEmpty()) ? customApiKey.trim() : (apiKey != null ? apiKey.trim() : null);

        if (activeKey != null && activeKey.length() > 10) {
            System.out.println("DEBUG - Kullanilan API Anahtari: " + activeKey.substring(0, 6) + "..." + activeKey.substring(activeKey.length() - 4));
        } else {
            System.out.println("DEBUG - Kullanilan API Anahtari: Bos veya cok kisa! (Uzunluk: " + (activeKey != null ? activeKey.length() : 0) + ")");
        }

        // API anahtarı boşsa veya tanımlanmadıysa yedek şablon döner
        if (activeKey == null || activeKey.trim().isEmpty()) {
            return getYedekTasarrufTavsiyesi(toplamGelir, toplamGider, netBakiye, tasarrufOrani, kategoriGiderleri);
        }

        try {
            String url = apiUrl + "?key=" + activeKey;

            // Request body
            Map<String, Object> requestBody = new HashMap<>();
            Map<String, Object> part = new HashMap<>();
            part.put("text", promptBuilder.toString());
            
            Map<String, Object> content = new HashMap<>();
            content.put("parts", List.of(part));
            
            requestBody.put("contents", List.of(content));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode candidates = root.path("candidates");
                if (candidates.isArray() && !candidates.isEmpty()) {
                    JsonNode textNode = candidates.get(0).path("content").path("parts").get(0).path("text");
                    return textNode.asText();
                }
            }
            return "Gemini API yanıtı okunamadı. Lütfen API ayarlarınızı kontrol edin.";

        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            e.printStackTrace();
            String errorDetail = e.getResponseBodyAsString();
            String userFriendlyError = "Gemini API bağlantısında bir hata oluştu.";

            if (errorDetail != null) {
                String errorLower = errorDetail.toLowerCase();
                if (errorLower.contains("api_key_invalid") || errorLower.contains("api key not valid") || errorLower.contains("invalid api key")) {
                    userFriendlyError = "Girdiğiniz Gemini API anahtarı geçersizdir. Lütfen doğru bir anahtar girdiğinizden emin olun.";
                } else if (errorLower.contains("not found") || errorLower.contains("not_found") || e.getStatusCode().value() == 404) {
                    userFriendlyError = "Yapay zeka modeli bulunamadı veya API anahtarınızın bu modele (gemini-2.5-flash) erişim yetkisi yok.";
                } else if (errorLower.contains("quota") || errorLower.contains("limit") || errorLower.contains("429")) {
                    userFriendlyError = "Yapay zeka API kotanız dolmuş veya hız limitine takılmış olabilirsiniz (Rate Limit).";
                }
            }

            return "⚠️ **Hata:** " + userFriendlyError + "\n\n" +
                    "Sistem sizin için otomatik yerel analiz üretti:\n\n" +
                    getYedekTasarrufTavsiyesi(toplamGelir, toplamGider, netBakiye, tasarrufOrani, kategoriGiderleri);
        } catch (Exception e) {
            e.printStackTrace();
            return "⚠️ **Hata:** Gemini API bağlantısında beklenmeyen bir hata oluştu. Sistem sizin için otomatik yerel analiz üretti:\n\n" +
                    getYedekTasarrufTavsiyesi(toplamGelir, toplamGider, netBakiye, tasarrufOrani, kategoriGiderleri);
        }
    }

    private String getYedekTasarrufTavsiyesi(double gelir, double gider, double bakiye, double tasarrufOrani, Map<?, ?> kategoriGiderleri) {
        StringBuilder sb = new StringBuilder();
        sb.append("⚠️ *Not: Google Gemini API anahtarı ayarlanmadığı veya bağlantı hatası oluştuğu için, Tasarruf Koçu sizin için yerel analiz motorunu kullandı.*\n\n");
        sb.append("### 1. Finansal Durum Özeti\n");
        sb.append(String.format("Toplam geliriniz **%,.2f TL** ve gideriniz **%,.2f TL** seviyesinde. ", gelir, gider));
        
        if (bakiye > 0) {
            sb.append(String.format("Bu ay **%,.2f TL** net tasarruf yaptınız. Tasarruf oranınız **%%%d**. Bu oran harika! Genel olarak bütçenizi kontrol altında tutabiliyorsunuz.\n\n", bakiye, (int)tasarrufOrani));
        } else {
            sb.append(String.format("Bu ay bütçeniz **%,.2f TL** açık verdi. Gelirinizden fazla harcama yapıyorsunuz. Acilen harcamaları kısmaya odaklanmalısınız.\n\n", Math.abs(bakiye)));
        }

        sb.append("### 2. Dikkat Çeken Harcamalar ve Fırsatlar\n");
        if (kategoriGiderleri.isEmpty()) {
            sb.append("Henüz girilmiş bir gider kaydı yok.\n\n");
        } else {
            sb.append("Harcamalarınızın kategorilere göre dağılımı incelendiğinde:\n");
            kategoriGiderleri.forEach((kat, tutar) -> {
                double tVal = ((Number) tutar).doubleValue();
                sb.append(String.format("- **%s**: %,.2f TL (%s oranında etki)\n", kat, tVal, 
                        gelir > 0 ? String.format("%%%d", (int)((tVal / gelir) * 100)) : "Belirsiz"));
            });
            sb.append("Özellikle en yüksek tutarlı kategorilerde harcamaları optimize etmek bütçenizde hızlı rahatlama sağlayacaktır.\n\n");
        }

        sb.append("### 3. Alternatif Senaryo Analizi\n");
        // Dışarıda yemek veya eğlence kategorileri varsa bunlara özel senaryo
        double yemekTutar = 0.0;
        if (kategoriGiderleri.containsKey("Yemek")) {
            yemekTutar = ((Number) kategoriGiderleri.get("Yemek")).doubleValue();
        } else if (kategoriGiderleri.containsKey("yemek")) {
            yemekTutar = ((Number) kategoriGiderleri.get("yemek")).doubleValue();
        }
        
        if (yemekTutar > 0) {
            sb.append(String.format("- **Senaryo A (Yemek Optimizasyonu)**: Yemek harcamalarınızı %%%d oranında azaltırsanız, her ay fazladan **%,.2f TL** tasarruf edebilirsiniz. Bu birikimi vadeli mevduat veya altın hesabı ile değerlendirebilirsiniz.\n", 30, yemekTutar * 0.3));
        } else {
            sb.append(String.format("- **Senaryo A**: En büyük harcama kaleminizi %%%d oranında optimize etmek, bütçenize doğrudan katkı sağlayacaktır.\n", 20));
        }
        
        sb.append(String.format("- **Senaryo B (Genel Tasarruf)**: Toplam giderlerinizden genel olarak %%%d tasarruf etmek size her ay **%,.2f TL** ek bakiye kazandıracaktır.\n\n", 10, gider * 0.10));

        sb.append("### 4. Tasarruf Koçu Eylem Planı\n");
        sb.append("1. **50/30/20 Kuralını Uygulayın**: Gelirinizin %50'sini ihtiyaçlara, %30'unu isteklere, %20'sini ise doğrudan tasarrufa ayırın.\n");
        sb.append("2. **Alışveriş Öncesi 24 Saat Kuralı**: Acil olmayan her istek alışverişi için karar vermeden önce 24 saat bekleyin.\n");
        sb.append("3. **Küçük Abonelikleri Gözden Geçirin**: Kullanmadığınız dijital platform üyeliklerini iptal edin.");

        return sb.toString();
    }
}

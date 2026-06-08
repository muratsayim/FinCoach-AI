# 🧠 FinCoach AI - Kişisel Finans Yönetim Sistemi

<div align="center">
  <img src="https://img.shields.io/badge/Java-17%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Boot 3.2.5" />
  <img src="https://img.shields.io/badge/Google_Gemini-API_v1-8E75C2?style=for-the-badge&logo=googlegemini&logoColor=white" alt="Gemini API" />
  <img src="https://img.shields.io/badge/H2_Database-Embedded-007ACC?style=for-the-badge&logo=databricks&logoColor=white" alt="H2 Database" />
  <img src="https://img.shields.io/badge/Frontend-Vanilla_CSS_Glassmorphism-blueviolet?style=for-the-badge" alt="Glassmorphism UI" />
</div>

---

### 📝 Proje Tanıtımı
> **"Geleceğinizi Finansal Zeka ile Şekillendirin"**  
**FinCoach AI**, kişisel bütçenizi kolayca kontrol altına almanızı sağlayan, harcama alışkanlıklarınızı dinamik grafiklerle görselleştiren ve entegre **Google Gemini Yapay Zeka** modeli sayesinde size özel tasarruf senaryoları üreten katmanlı mimariye sahip modern bir Spring Boot web uygulamasıdır.

---

## 🌟 Temel Özellikler

* **🔐 Güvenli Giriş & Kayıt (Auth):** Bireysel kullanıcılar için SHA-256 şifreleme algoritmalı güvenli kayıt ve giriş arayüzü.
* **📂 Tam Veri İzolasyonu (`X-User-Id`):** Her kullanıcının verileri tamamen kendine özeldir. API uç noktalarında sahiplik kontrolü yapılarak verileriniz diğer kullanıcılardan izole edilir.
* **💸 Gelir & Gider Yönetimi:** Gelir/gider ekleme, silme, listeleme ve kategori bazlı (yemek, fatura, eğlence, maaş vb.) filtreleme.
* **📈 Dinamik Grafikler (Chart.js):** Harcama dağılımınızı gösteren Doughnut (Döngüsel) grafiği ve aylık gelir-gider dengesini gösteren Bar (Sütun) grafiği.
* **🤖 Yapay Zeka Tasarruf Koçu (Gemini Entegrasyonu):** Finansal durumunuzu analiz ederek borçlanma yükünüzü azaltacak kişiselleştirilmiş prompt mühendisliği tabanlı tasarruf tavsiyeleri ve alternatif bütçe senaryoları.
* **⚡ Hızlı ve Kararlı Veritabanı:** Geliştirme ortamında disk üzerinde kalıcı olarak saklanan dosya tabanlı H2 Database; unit testleri için ise izole edilmiş bellek içi (in-memory) H2 test veritabanı profil sistemi (Hızlı derleme ve sıfır çakışma).

---

## 🛠️ Kullanılan Teknolojiler

| Katman | Teknoloji | Açıklama |
| :--- | :--- | :--- |
| **Backend** | `Java 17+` / `Spring Boot 3.2.5` | RESTful API, Service Katmanı, Global Exception Handling |
| **Veri Tabanı** | `H2 Database (Embedded)` | Disk üzerinde kalıcı yerel veritabanı (MSSQL Server desteği hazır) |
| **ORM** | `Spring Data JPA` / `Hibernate` | Veritabanı tablo eşlemeleri ve sorgu yönetimi |
| **Yapay Zeka** | `Google Gemini API` | Harcama analizi ve akıllı tasarruf prompt motoru |
| **Arayüz (UI)** | `HTML5` / `Vanilla CSS3` | Glassmorphism (Cam Morfizmi) Tasarımı, Koyu Tema |
| **Kütüphaneler** | `Chart.js`, `Marked.js`, `FontAwesome` | Dinamik grafikler, Markdown render işlemi ve zengin ikon seti |
| **Test** | `JUnit 5` / `Spring Boot Test` | `@ActiveProfiles("test")` ile izole in-memory test ortamı |

---

## 📋 Scrum (Agile) Görev Dağılımı ve Roller

Proje geliştirme sürecinde belirlenen Epic'ler, User Story'ler ve ekip üyelerinin rol dağılımları şu şekildedir:

### 🏢 EPIC 1: Altyapı ve Güvenli Veritabanı Mimarisinin Kurulması
> **User Story 1:** Bir bireysel kullanıcı olarak, finansal verilerimin güvenli bir veritabanı mimarisinde saklanmasını ve sistemin kararlı çalışmasını istiyorum.
* **Sub-task 1.1:** Spring Boot projesinin temel backend mimarisinin ve klasör yapısının oluşturulması.  ➡️ **Taha**
* **Sub-task 1.2:** Veritabanı şemasının H2 üzerinde tasarlanması.  ➡️ **Taha**
* **Sub-task 1.3:** Hibernate ORM bağımlılıklarının eklenmesi, veritabanı testlerinin yapılması ve veri modellerinin (Kullanici, KullaniciRepository) oluşturulması.  ➡️ **Taha**

### 💸 EPIC 2: Gelir-Gider Takibi ve Harcama Analizi Çekirdek Modülü
> **User Story 2:** Bütçesini kontrol etmekte zorlanan bir kullanıcı olarak, gelir ve giderlerimi kategorize ederek sisteme kaydedebilmeyi ve harcama alışkanlıklarımın analiz edilmesini istiyorum.
* **Sub-task 2.1:** Gelir-gider verilerini eklemeye, silmeye yarayan REST API uç noktalarının (Endpoint) yazılması.  ➡️ **Muhammed**
* **Sub-task 2.2:** Verilerin kategorilere (yemek, ulaşım vb.) ayrılmasını sağlayan mantıksal yapının kurulması.  ➡️ **Muhammed**
* **Sub-task 2.3:** Kullanıcının geçmiş bütçe verilerini analiz eden backend algoritmalarının geliştirilmesi.  ➡️ **Muhammed**

### 🤖 EPIC 3: "Tasarruf Koçu" ve Yapay Zeka Danışmanlık Modülü
> **User Story 3:** Borçlanma yükünü azaltmak ve finansal okuryazarlık kazanmak isteyen bir birey olarak, geçmiş harcamalarıma göre bana özel üretilmiş alternatif tasarruf senaryoları görmek istiyorum.
* **Sub-task 3.1:** Google Gemini API bağlantısının Spring Boot projesine entegre edilmesi.  ➡️ **Murat**
* **Sub-task 3.2:** Kullanıcının geçmiş verilerini analiz edip anlamlı bir prompt haline getiren yapay zeka katmanının kodlanması.  ➡️ **Murat**
* **Sub-task 3.3:** Gemini API'den dönen yanıtların test edilmesi ve alternatif tasarruf senaryolarına dönüştürülmesi.  ➡️ **Murat**

### 💻 EPIC 4: Kullanıcı Dostu UI/UX ve Dashboard Geliştirme
> **User Story 4:** Finansal farkındalık kazanmak isteyen bir öğrenci veya beyaz yakalı olarak, karmaşık olmayan ve harcamalarımı anında analiz edebileceğim kullanıcı dostu bir arayüz kullanmak istiyorum.
* **Sub-task 4.1:** Glassmorphism temalı modern UI/UX arayüz stil tasarımlarının (CSS) hazırlanması.  ➡️ **Muhammed**
* **Sub-task 4.2:** Gelir-gider grafiklerini ve AI destekli danışmanlık modülünden gelen tavsiyeleri gösteren ana ekranın (Dashboard) kodlanması ve arayüz testlerinin tamamlanması.  ➡️ **Murat**

### 🔐 EPIC 5: Çoklu Kullanıcı Güvenliği ve Oturum Sistemi
> **User Story 5:** Bireysel bir kullanıcı olarak, finansal kayıtlarımın diğer kullanıcılardan tamamen izole edilmesini ve oturumumu güvenli bir şekilde açıp kapatabilmeyi istiyorum.
* **Sub-task 5.1:** Giriş ve Kayıt (Login/Register) ekran tasarımları ve backend API uç noktalarının oluşturulması.  ➡️ **Murat**
* **Sub-task 5.2:** Şifrelerin SHA-256 ile hash'lenmesi ve oturumun `sessionStorage` ile yönetilmesi.  ➡️ **Murat**
* **Sub-task 5.3:** `X-User-Id` HTTP başlığı kontrolüyle API sahiplik kontrollerinin ve JUnit test veritabanı izolasyonunun yapılması.  ➡️ **Taha** (Test İzolasyonu), **Muhammed** (İşlem Kontrolü), **Murat** (AI Kontrolü ve Frontend)

---

## 🗂️ Dosya Yükleme Rehberi (Ekip Üyelerine Göre)

Aşağıdaki listeyi, ekip üyelerinin GitHub'a yapacağı yüklemelerde dosya bazlı commit şablonu olarak kullanabilirsiniz:

### ⚙️ 1. TAHA'NIN YÜKLEYECEĞİ DOSYALAR (Toplam: 13 Dosya)
1. **`pom.xml`**  
   * **Görev:** `Sub-task 1.1` (Backend Temel Yapısı)  
   * **Commit Mesajı:** `Sub-task 1.1: Maven projesi olusturuldu, Spring Boot 3.2.5 ve gerekli dependency yapilandirmalari pom.xml dosyasina eklendi.`
2. **`src/main/resources/application.properties`**  
   * **Görev:** `Sub-task 1.1` (Ana Konfigürasyon)  
   * **Commit Mesajı:** `Sub-task 1.1: Proje ana konfigürasyonu olusturuldu, dev profili ve Gemini API url tanimlamalari yapildi.`
3. **`src/main/java/com/finans/yonetim/FinansYonetimApplication.java`**  
   * **Görev:** `Sub-task 1.1` (Giriş Sınıfı)  
   * **Commit Mesajı:** `Sub-task 1.1: Projenin ana giris noktası olan Spring Boot uygulama sınıfı olusturuldu.`
4. **`src/main/java/com/finans/yonetim/exception/GlobalExceptionHandler.java`**  
   * **Görev:** `Sub-task 1.1` (Hata Kontrolü)  
   * **Commit Mesajı:** `Sub-task 1.1: Sunucu genelinde fırlatılan hataları yakalayip JSON formatında geriye dönen hata kontrol sınıfı yazildi.`
5. **`.gitignore`**  
   * **Görev:** `Sub-task 1.1` (Git Dışı Dosyalar)  
   * **Commit Mesajı:** `Sub-task 1.1: Target, IDE dosyalari ve H2 veri dosyalarinin Git takibine takilmamasi için .gitignore olusturuldu.`
6. **`src/main/resources/application-dev.properties`**  
   * **Görev:** `Sub-task 1.2` (H2 Geliştirme Ayarları)  
   * **Commit Mesajı:** `Sub-task 1.2: Gelisme ortami için yerel H2 veritabanı bağlantısı ve yerel disk kalıcılık (dev profili) ayarları yapildi.`
7. **`src/main/resources/application-prod.properties`**  
   * **Görev:** `Sub-task 1.2` (Üretim MSSQL Ayarları)  
   * **Commit Mesajı:** `Sub-task 1.2: Uretim ortami icin MSSQL sunucu baglanti profil konfigurasyonlari hazirlandi.`
8. **`src/main/java/com/finans/yonetim/model/Kullanici.java`**  
   * **Görev:** `Sub-task 1.3` (Hibernate ORM ve Model Yapıları)  
   * **Commit Mesajı:** `Sub-task 1.3: Kullanici entity sinifi olusturularak veritabani tablo eslemesi yapildi.`
9. **`src/main/java/com/finans/yonetim/repository/KullaniciRepository.java`**  
   * **Görev:** `Sub-task 1.3` (Hibernate ORM ve Model Yapıları)  
   * **Commit Mesajı:** `Sub-task 1.3: Kullanici verilerinin sorgulanmasi icin KullaniciRepository arayuzu yazildi.`
10. **`src/main/resources/application-test.properties`**  
    * **Görev:** `Sub-task 5.3` (Bellek İçi Test Veritabanı İzolasyonu)  
    * **Commit Mesajı:** `Sub-task 5.3: Unit testlerin gelistirme veritabanini sifirlamasini onlemek amacıyla izole bellek ici H2 test veritabani profili olusturuldu.`
11. **`src/test/java/com/finans/yonetim/FinansYonetimApplicationTests.java`**  
    * **Görev:** `Sub-task 5.3` (JUnit Test Sınıfı)  
    * **Commit Mesajı:** `Sub-task 5.3: FinansYonetimApplicationTests sinifi @ActiveProfiles("test") ile izole test profiline baglanarak guncellendi.`
12. **`src/main/java/com/finans/yonetim/controller/IslemController.java`**  
    * **Görev:** `Sub-task 2.1` & `5.3` (İşlem API Denetleyici ve Veri Sahipliği)  
    * **Commit Mesajı:** `Sub-task 2.1 & 5.3: Gelir-gider CRUD REST API uç noktaları entegre edildi ve X-User-Id sahiplik kontrolü eklendi.`
13. **`src/main/resources/static/index.html`**  
    * **Görev:** `Sub-task 4.1`, `4.2` & `5.1` (UI/UX Tasarımı, Dashboard ve Auth Ekranları)  
    * **Commit Mesajı:** `Sub-task 4.1 & 4.2 & 5.1: HTML iskeleti, CSS glassmorphism stilleri ve Chart.js/sessionStorage JS kodları entegre edildi.`

### 💸 2. MUHAMMED'İN YÜKLEYECEĞİ DOSYALAR (Toplam: 5 Dosya)
1. **`src/main/java/com/finans/yonetim/model/IslemTur.java`**  
   * **Görev:** `Sub-task 2.2` (İşlem Türü Enumu)  
   * **Commit Mesajı:** `Sub-task 2.2: Gelir ve Gider ayrımını yapacak IslemTur enum yapısı olusturuldu.`
2. **`src/main/java/com/finans/yonetim/model/Islem.java`**  
   * **Görev:** `Sub-task 2.2` & `5.3` (İşlem Entity & Sahiplik)  
   * **Commit Mesajı:** `Sub-task 2.2 & 5.3: Islem entity sinifi olusturuldu, Kullanici nesnesine @ManyToOne ile baglanarak veri izolasyonu alt yapisi saglandi.`
3. **`src/main/java/com/finans/yonetim/repository/IslemRepository.java`**  
   * **Görev:** `Sub-task 2.1` & `5.3` (İşlem Repository)  
   * **Commit Mesajı:** `Sub-task 2.1 & 5.3: Verileri kullanici id degerine gore getiren IslemRepository veritabani sorgu arayuzu kodlandi.`
4. **`src/main/java/com/finans/yonetim/service/IslemService.java`**  
   * **Görev:** `Sub-task 2.1` & `5.3` (İşlem Servis Arayüzü)  
   * **Commit Mesajı:** `Sub-task 2.1 & 5.3: Kullanıcı bazlı bütçe CRUD işlemlerini tanımlayan servis arayüzü yazıldı.`
5. **`src/main/java/com/finans/yonetim/service/IslemServiceImpl.java`**  
   * **Görev:** `Sub-task 2.1`, `2.3` & `5.3` (İşlem Servis Gerçekleşimi ve Analiz)  
   * **Commit Mesajı:** `Sub-task 2.1 & 2.3 & 5.3: Gelir-gider iş mantığı, kullanıcı bazlı veri filtreleme ve analiz algoritmaları kodlandı.`

### 🤖 3. MURAT'IN YÜKLEYECEĞİ DOSYALAR (Toplam: 5 Dosya)
1. **`src/main/java/com/finans/yonetim/service/GeminiService.java`**  
   * **Görev:** `Sub-task 3.1` & `3.2` (Gemini Yapay Zeka Servisi)  
   * **Commit Mesajı:** `Sub-task 3.1 & 3.2: Gemini API entegrasyonu, tasarruf öneri prompt tasarımı ve çevrimdışı analiz motoru kodlandı.`
2. **`src/main/java/com/finans/yonetim/controller/TasarrufKocuController.java`**  
   * **Görev:** `Sub-task 3.3` & `5.3` (Tasarruf Koçu API Denetleyici)  
   * **Commit Mesajı:** `Sub-task 3.3 & 5.3: TasarrufKocuController REST API uc noktasi kodlandi ve X-User-Id basligi ile API sahiplik kontrolleri entegre edildi.`
3. **`src/main/java/com/finans/yonetim/controller/AuthController.java`**  
   * **Görev:** `Sub-task 5.1` (Kullanıcı Giriş/Kayıt API Denetleyici)  
   * **Commit Mesajı:** `Sub-task 5.1: Oturum açma ve kayıt olma isteklerini yöneten AuthController REST API uç noktaları yazıldı.`
4. **`src/main/java/com/finans/yonetim/service/KullaniciService.java`**  
   * **Görev:** `Sub-task 5.2` (Kullanıcı Servis Arayüzü)  
   * **Commit Mesajı:** `Sub-task 5.2: Giriş ve kayıt işlemlerini tanımlayan KullaniciService arayüzü yazıldı.`
5. **`src/main/java/com/finans/yonetim/service/KullaniciServiceImpl.java`**  
   * **Görev:** `Sub-task 5.2` (Şifre Güvenliği ve Oturum Servis Gerçekleşimi)  
   * **Commit Mesajı:** `Sub-task 5.2: Şifrelerin SHA-256 algoritmasıyla hash'lenmesi ve kullanıcı oturum doğrulama iş mantığı kodlandı.`

### 📂 4. ORTAK PROJE YÖNETİM DOSYALARI
1. **`README.md`**  
   * **Görev:** Genel Proje Belgeleri  
   * **Commit Mesajı:** `docs: Proje kapsamı, Agile görev dağılımları ve dosya yükleme rehberini içeren README.md belgesi hazırlandı.`
2. **`run.bat`**  
   * **Görev:** Proje Hızlı Çalıştırma Aracı  
   * **Commit Mesajı:** `tools: Spring Boot uygulamasını derleyen ve tarayıcıyı localhost:8080 adresinden otomatik olarak başlatan run.bat aracı eklendi.`

---

## 📁 Proje Klasör Yapısı (Katmanlı Mimari)

```text
finans-yonetim-sistemi/
│
├── src/
│   ├── main/
│   │   ├── java/com/finans/yonetim/
│   │   │   ├── controller/      # API Uç Noktaları (Auth, Islem, TasarrufKocu)
│   │   │   ├── exception/       # Hata Yakalama Sınıfları (GlobalExceptionHandler)
│   │   │   ├── model/           # Veritabanı Modelleri (Islem, Kullanici, IslemTur)
│   │   │   ├── repository/      # Veritabanı Sorguları (IslemRepository, KullaniciRepository)
│   │   │   ├── service/         # İş Mantığı Sınıfları (IslemService, KullaniciService, GeminiService)
│   │   │   └── FinansYonetimApplication.java   # Uygulama Başlangıç Sınıfı
│   │   │
│   │   └── resources/
│   │       ├── static/          # Arayüz Dosyaları (index.html, JS/CSS kodları)
│   │       ├── application.properties         # Ortak Uygulama Ayarları
│   │       ├── application-dev.properties     # Yerel Veritabanı Ayarı
│   │       ├── application-prod.properties    # Üretim MSSQL Ayarı
│   │       └── application-test.properties    # JUnit Test Veritabanı Ayarı
│   │
│   └── test/java/com/finans/yonetim/
│       └── FinansYonetimApplicationTests.java  # Entegrasyon Test Sınıfı
│
├── .gitignore                   # Git Tarafından Yok Sayılacak Dosyalar Listesi
├── pom.xml                      # Maven Bağımlılıkları Tanımlama Dosyası
├── run.bat                      # Windows Kolay Çalıştırma Betiği
└── README.md                    # Proje Açıklama ve Tanıtım Dosyası
```

---

## 🚀 Projeyi Çalıştırma Kılavuzu

### ⚙️ Sistem Gereksinimleri
* Bilgisayarınızda **Java JDK 17** veya üzeri bir sürümün kurulu olması gerekmektedir.
* Google Gemini yapay zeka özelliklerinin çalışabilmesi için geçerli bir internet bağlantısı bulunmalıdır.

### 🔌 Yöntem 1: Çift Tıklayarak Çalıştırma (En Kolay Yol)
1. Proje kök dizininde bulunan **`run.bat`** dosyasına çift tıklayarak çalıştırın.
2. Maven bağımlılıkları yüklendikten sonra Spring Boot otomatik olarak başlatılacaktır.
3. Uygulama hazır olduğunda varsayılan tarayıcınızda otomatik olarak **`http://localhost:8080`** adresi açılacaktır.

### 🛠️ Yöntem 2: IntelliJ IDEA (veya Diğer IDE'ler) ile Çalıştırma
1. IDE'nizi açın ve **"Open"** seçeneğiyle bu proje klasörünü (içinde `pom.xml` olan dizin) seçin.
2. Maven'ın bağımlılıkları indirmesini bekleyin.
3. `src/main/java/com/finans/yonetim/FinansYonetimApplication.java` dosyasını açıp yeşil **Run** butonuna tıklayın.
4. Tarayıcınızdan **`http://localhost:8080`** adresine gidin.

### 💻 Yöntem 3: Komut Satırından Maven ile Çalıştırma
Proje klasörünün kök dizininde bir terminal açıp aşağıdaki komutu çalıştırın:
```bash
mvn spring-boot:run
```
Ardından tarayıcınızdan **`http://localhost:8080`** adresine erişim sağlayın.

---

## 🧪 Birim Testlerin (Unit Tests) Çalıştırılması
Geliştirme veritabanından tamamen izole bellek içi test profilini çalıştırarak testlerin doğruluğunu kontrol etmek için aşağıdaki komutu çalıştırabilirsiniz:
```bash
mvn clean test
```
*Testler esnasında `application-test.properties` devreye girerek yerel verilerinizin silinmesini önler.*

---
*© 2026 FinCoach AI. Tüm hakları saklıdır.*

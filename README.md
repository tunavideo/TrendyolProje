# 🛒 SmartTrendyol - Akıllı Alışveriş Sistemi

## 📋 İçindekiler
- [Proje Hakkında](#proje-hakkında)
- [Kullanılan Teknolojiler](#kullanılan-teknolojiler)
- [Proje Yapısı](#proje-yapısı)
- [Veritabanı Yapısı](#veritabanı-yapısı)
- [Özellikler ve Modüller](#özellikler-ve-modüller)
- [Kurulum ve Çalıştırma](#kurulum-ve-çalıştırma)
- [Sınava Çalışma Notları](#sınava-çalışma-notları)

---

## 🎯 Proje Hakkında

**SmartTrendyol**, Java Swing ve MongoDB kullanılarak geliştirilmiş bir e-ticaret uygulamasıdır. Kullanıcıların ürünleri görüntüleyebildiği, sepete ekleyebildiği, favorilere kaydedebildiği, sipariş oluşturabildiği ve kargo takibi yapabildiği; yöneticilerin ise stok takibi ve güncelleme işlemlerini gerçekleştirebildiği modern bir alışveriş platformudur.

### Proje Özellikleri
- ✅ Kullanıcı ve Yönetici arayüzleri
- ✅ Ürün listeleme ve filtreleme
- ✅ Sepet yönetimi
- ✅ Favori ürünler listesi
- ✅ Sipariş geçmişi
- ✅ Kargo takibi
- ✅ Ödeme sistemi
- ✅ Stok takibi ve yönetimi
- ✅ Modern ve responsive tasarım

---

## 💻 Kullanılan Teknolojiler

| Teknoloji | Versiyon | Kullanım Alanı |
|-----------|----------|----------------|
| **Java** | 17 | Programlama dili |
| **Maven** | - | Proje yönetimi ve bağımlılık yönetimi |
| **MongoDB** | 5.1.0 | Veritabanı yönetimi |
| **Swing** | - | Grafiksel kullanıcı arayüzü (GUI) |
| **MongoDB Driver Sync** | 5.1.0 | MongoDB ile Java arasındaki iletişim |

### Maven Bağımlılıkları
```xml
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>5.1.0</version>
</dependency>
```

---

## 📁 Proje Yapısı

```
akilliproje/
├── src/main/java/com/trendyol/proje/
│   ├── Main.java                      # Uygulama giriş noktası
│   ├── MainFrame.java                 # Ana çerçeve (CardLayout yapısı)
│   ├── LoginUI.java                   # Giriş ekranı
│   ├── UserHomeUI.java                # Kullanıcı ana sayfası
│   ├── AdminUI.java                   # Yönetici paneli
│   ├── CartUI.java                    # Sepet arayüzü
│   ├── FavoritesUI.java               # Favoriler arayüzü
│   ├── OrderHistoryUI.java            # Sipariş geçmişi arayüzü
│   ├── PaymentUI.java                 # Ödeme arayüzü
│   ├── CargoTrackingUI.java           # Kargo takibi arayüzü
│   ├── DatabaseManager.java           # Veritabanı işlemleri
│   ├── Style.java                     # Stil ve tasarım sabitleri
│   └── gerekligereksiz/               # Veri ekleme yardımcı sınıfları
│       ├── InsertProducts.java
│       └── InsertInitialData.java
├── resimler/                          # Ürün görselleri
├── pom.xml                            # Maven yapılandırma dosyası
└── README.md                          # Bu dosya
```

---

## 🗄️ Veritabanı Yapısı

### MongoDB Koleksiyonları

#### 1. Products (Ürünler)
```json
{
  "_id": ObjectId,
  "name": String,
  "price": Double,
  "stock": Integer,
  "imagePath": String
}
```

#### 2. Basket (Sepet)
```json
{
  "_id": ObjectId,
  "name": String,
  "price": Double,
  "stock": Integer,
  "imagePath": String
}
```

#### 3. Favorites (Favoriler)
```json
{
  "_id": ObjectId,
  "name": String,
  "price": Double,
  "stock": Integer,
  "imagePath": String
}
```

#### 4. OrderHistory (Sipariş Geçmişi)
```json
{
  "_id": ObjectId,
  "date": Date,
  "total": Double,
  "address": String,
  "paymentMethod": String,
  "status": String,
  "details": String
}
```

### Veritabanı Bağlantısı
```java
String uri = "mongodb://adminuser:strongpassword123@localhost:27300/?authSource=admin";
MongoClient client = MongoClients.create(uri);
MongoDatabase db = client.getDatabase("SmartTrendyolDB");
```

---

## 🎨 Özellikler ve Modüller

### 1. Giriş Ekranı (LoginUI)
- **Dosya:** `LoginUI.java`
- **Özellikler:**
  - Müşteri girişi butonu
  - Yönetici girişi butonu
  - Modern ve minimalist tasarım

### 2. Kullanıcı Ana Sayfası (UserHomeUI)
- **Dosya:** `UserHomeUI.java`
- **Özellikler:**
  - Ürün kartları (4 sütunlu grid yapısı)
  - Ürün resmi, ismi ve fiyatı
  - Sepete ekleme butonu
  - Favorilere ekleme butonu
  - Üst navigasyon menüsü (Favoriler, Sepet, Sipariş Geçmişi, Kargo Takip)

### 3. Yönetici Paneli (AdminUI)
- **Dosya:** `AdminUI.java`
- **Özellikler:**
  - Ürün stok tablosu
  - Stok güncelleme dialogu
  - Kritik stok uyarısı (stok ≤ 15)
  - Tablo yenileme butonu
  - Çıkış butonu

### 4. Sepet Arayüzü (CartUI)
- **Dosya:** `CartUI.java`
- **Özellikler:**
  - Sepetteki ürünlerin listelenmesi
  - Toplam fiyat hesaplama
  - Ödeme sayfasına geçiş

### 5. Favoriler Arayüzü (FavoritesUI)
- **Dosya:** `FavoritesUI.java`
- **Özellikler:**
  - Favori ürünlerin listelenmesi
  - Favorilerden sepete ekleme

### 6. Sipariş Geçmişi (OrderHistoryUI)
- **Dosya:** `OrderHistoryUI.java`
- **Özellikler:**
  - Geçmiş siparişlerin listelenmesi
  - Sipariş detayları (tarih, tutar, adres, durum)

### 7. Ödeme Arayüzü (PaymentUI)
- **Dosya:** `PaymentUI.java`
- **Özellikler:**
  - Ödeme yöntemi seçimi
  - Adres bilgisi girişi
  - Sipariş oluşturma

### 8. Kargo Takibi (CargoTrackingUI)
- **Dosya:** `CargoTrackingUI.java`
- **Özellikler:**
  - Sipariş durumunu görüntüleme
  - Kargo adımlarını gösterme

### 9. Veritabanı Yöneticisi (DatabaseManager)
- **Dosya:** `DatabaseManager.java`
- **Temel Metotlar:**

| Metot | Açıklama |
|-------|----------|
| `getProducts()` | Tüm ürünleri getirir |
| `addToBasket()` | Ürünü sepete ekler, stok düşer |
| `addToFavorites()` | Ürünü favorilere ekler |
| `getFavorites()` | Favori ürünleri getirir |
| `getBasket()` | Sepetteki ürünleri getirir |
| `createOrder()` | Yeni sipariş oluşturur |
| `updateProductStock()` | Ürün stoğunu günceller |
| `getOrderHistory()` | Sipariş geçmişini getirir |
| `updateSpecificOrderStatus()` | Sipariş durumunu günceller |

### 10. Stil Yöneticisi (Style)
- **Dosya:** `Style.java`
- **Tasarım Sabitleri:**
  - Renk paleti (Turuncu, Koyu Gri, Beyaz, vb.)
  - Font tanımları (Başlık, Kalın, Kart, Fiyat)
  - Kart stilleri ve gölgelendirme
  - Buton stilleri (Ana ve İkincil butonlar)

---

## 🚀 Kurulum ve Çalıştırma

### Ön Koşullar
- Java Development Kit (JDK) 17 veya üzeri
- Apache Maven
- MongoDB (localhost:27300 portunda çalışmalı)

### Adım 1: MongoDB'yi Başlatın
```bash
# MongoDB'yi başlatın (port: 27300)
mongod --port 27300
```

### Adım 2: Projeyi Derleyin
```bash
# Proje dizinine gidin
cd T:/MyProject/sila/akilliproje

# Projeyi Maven ile derleyin
mvn clean compile
```

### Adım 3: Uygulamayı Çalıştırın
```bash
# Maven ile çalıştırın
mvn exec:java -Dexec.mainClass="com.trendyol.proje.Main"
```

Veya IDE'den (IntelliJ IDEA, Eclipse, vb.) `Main.java` dosyasını çalıştırın.

### Adım 4: Veritabanına Başlangıç Verisi Ekleme (İsteğe Bağlı)
```bash
# InsertInitialData.java sınıfını çalıştırarak örnek veriler ekleyebilirsiniz
```

---

## 📚 Sınava Çalışma Notları

### Önemli Kavramlar

#### 1. Java Swing Kavramları
- **JFrame:** Ana pencere çerçevesi
- **JPanel:** İçerik panelleri
- **JButton:** Buton bileşeni
- **JLabel:** Metin ve resim gösterme bileşeni
- **JTable:** Tablo gösterme bileşeni
- **JScrollPane:** Kaydırılabilir alan
- **CardLayout:** Sayfalar arası geçiş için layout
- **GridLayout:** Grid yapısı (satır ve sütun bazlı)
- **BorderLayout:** Kuzey, Güney, Doğu, Batı ve Merkez bölgeleri
- **BoxLayout:** Dikey veya yatay hizalama

#### 2. MongoDB Kavramları
- **Document:** JSON benzeri veri yapısı
- **Collection:** Tablo benzeri yapı
- **ObjectId:** Benzersiz tanımlayıcı
- **find():** Veri sorgulama
- **insertOne():** Tek veri ekleme
- **updateOne():** Tek veri güncelleme
- **$inc:** Değer artırma operatörü
- **$set:** Değer atama operatörü

#### 3. Tasarım Desenleri
- **Singleton Pattern:** Tek örnekleme (DatabaseManager)
- **Observer Pattern:** Event handling (ActionListener)
- **MVC Pattern:** Model-View-Controller ayrımı

#### 4. Önemli Kod Parçaları

**CardLayout ile Sayfa Geçişi:**
```java
CardLayout cardLayout = new CardLayout();
JPanel mainPanel = new JPanel(cardLayout);
mainPanel.add(new UserHomeUI(this), "Home");
mainPanel.add(new CartUI(this), "Cart");
cardLayout.show(mainPanel, "Cart"); // Sayfa değiştirme
```

**MongoDB Veri Ekleme:**
```java
Document product = new Document("name", "iPhone 15")
    .append("price", 45000.0)
    .append("stock", 50);
db.getCollection("Products").insertOne(product);
```

**MongoDB Veri Güncelleme:**
```java
db.getCollection("Products").updateOne(
    new Document("_id", productId),
    new Document("$inc", new Document("stock", -1))
);
```

**ActionListener Kullanımı:**
```java
btn.addActionListener(e -> {
    // Tıklama işlemleri
    new MainFrame();
    this.dispose();
});
```

### Sınav Soruları İçin Önemli Noktalar

1. **Neden MongoDB kullanıldı?**
   - Esnek şema yapısı
   - JSON benzeri belge yapısı
   - Hızlı sorgulama
   - Ölçeklenebilirlik

2. **CardLayout nedir ve ne için kullanıldı?**
   - Sayfalar arası geçiş için kullanılan bir layout
   - Bu projede Home, Cart, Favorites ve History sayfaları arasında geçiş için kullanıldı

3. **Stok düşme işlemi nasıl yapılıyor?**
   - Sepete ekleme sırasında `$inc` operatörü ile stok değeri 1 azaltılıyor
   - Atomic işlem olduğu için veri tutarlılığı sağlanıyor

4. **Neden Swing kullanıldı?**
   - Java'nın standart GUI kütüphanesi
   - Platform bağımsız çalışma
   - Kolay öğrenme ve kullanım

5. **Admin panelinde kritik stok nasıl belirleniyor?**
   - Stok değeri 15 ve altında olan ürünler kritik olarak işaretleniyor
   - Renkli uyarı ile gösteriliyor (kırmızı arka plan)

6. **Sipariş oluşturma süreci nedir?**
   - Sepetteki ürünler listelenir
   - Ödeme yöntemi ve adres seçilir
   - `createOrder()` metodu ile sipariş veritabanına kaydedilir
   - Sepet koleksiyonu temizlenir

7. **Style sınıfı ne işe yarıyor?**
   - Tüm tasarım sabitlerini tek bir yerde toplar
   - Kod tekrarını önler
   - Tasarım tutarlılığı sağlar

8. **MongoDB bağlantısı nasıl yapılıyor?**
   - `MongoClients.create()` ile bağlantı oluşturulur
   - `getDatabase()` ile veritabanı seçilir
   - `getCollection()` ile koleksiyon seçilir

9. **JTable'da hücre düzenlemesi nasıl engelleniyor?**
   - `DefaultTableModel`'in `isCellEditable()` metodu override edilir
   - `false` döndürülerek hücreler salt okunur yapılır

10. **Resim yükleme işlemi nasıl yapılıyor?**
    - `ImageIcon` sınıfı kullanılır
    - `getScaledInstance()` ile resim boyutlandırılır
    - Hata yönetimi ile olası sorunlar handle edilir

### Önemli Metotlar ve Parametreleri

| Metot | Parametreler | Dönüş Değeri |
|-------|--------------|--------------|
| `addToBasket()` | `Document product` | `void` |
| `addToFavorites()` | `Document product` | `void` |
| `createOrder()` | `String payMethod, String address, double total, String productDetails` | `String` (orderId) |
| `updateProductStock()` | `String productId, int newStock` | `void` |
| `updateSpecificOrderStatus()` | `String orderId, String status` | `boolean` |
| `getProducts()` | - | `List<Document>` |
| `getBasket()` | - | `List<Document>` |
| `getFavorites()` | - | `List<Document>` |
| `getOrderHistory()` | - | `List<Document>` |

### Sınav İçin İpuçları

1. **Veritabanı işlemlerini iyi anlayın:** CRUD operasyonları (Create, Read, Update, Delete)
2. **Swing bileşenlerini tanıyın:** JFrame, JPanel, JButton, JTable, JLabel, vb.
3. **Layout yöneticelerini bilin:** BorderLayout, GridLayout, CardLayout, BoxLayout
4. **Event handling'i anlayın:** ActionListener, MouseListener
5. **MongoDB sorgularını öğrenin:** find(), insertOne(), updateOne(), deleteOne()
6. **Projeyi çalıştırın ve test edin:** Her özelliği deneyimleyin
7. **Kodları okuyun ve analiz edin:** Her sınıfın ne işe yaradığını anlayın

---

## 👥 Geliştirici Bilgileri

- **Proje Adı:** SmartTrendyol - Akıllı Alışveriş Sistemi
- **Geliştirme Dili:** Java 17
- **Veritabanı:** MongoDB
- **GUI Framework:** Java Swing
- **Build Tool:** Maven

---

## 📄 Lisans

Bu proje eğitim amaçlı geliştirilmiştir.

---

## 📞 İletişim

Sorularınız için proje geliştiricisine başvurabilirsiniz.

---

**© 2024 SmartTrendyol Projesi - Tüm Hakları Saklıdır**

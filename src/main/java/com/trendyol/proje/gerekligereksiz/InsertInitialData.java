package com.trendyol.proje.gerekligereksiz;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class InsertInitialData {
    public static void main(String[] args) {
        try {
            // MongoDB bağlantısı
            MongoClient client = MongoClients.create(
                "mongodb://adminuser:strongpassword123@localhost:27300/?authSource=admin"
            );
            MongoDatabase db = client.getDatabase("SmartTrendyolDB");

            // Products koleksiyonu
            MongoCollection<Document> productsCollection = db.getCollection("Products");

            // Suppliers koleksiyonu
            MongoCollection<Document> suppliersCollection = db.getCollection("Suppliers");

            // Ürünleri temizle (isteğe bağlı)
            productsCollection.deleteMany(new Document());
            suppliersCollection.deleteMany(new Document());

            // === ÜRÜNLER ===
            List<Document> products = new ArrayList<>();

            products.add(new Document("name", "Kiko Milano Maskara")
                .append("category", "Kozmetik")
                .append("price", 450.00)
                .append("stock", 3)
                .append("imageName", "maskara.jpg"));

            products.add(new Document("name", "Oversize Denim Ceket")
                .append("category", "Tekstil")
                .append("price", 899.90)
                .append("stock", 12)
                .append("imageName", "ceket.jpg"));

            products.add(new Document("name", "La Roche-Posay Güneş Kremi")
                .append("category", "Kozmetik")
                .append("price", 620.00)
                .append("stock", 25)
                .append("imageName", "gunes_kremi.jpg"));

            products.add(new Document("name", "Pamuklu Çizgili Gömlek")
                .append("category", "Tekstil")
                .append("price", 350.00)
                .append("stock", 4)
                .append("imageName", "gomlek.jpg"));

            products.add(new Document("name", "L'Oreal Revitalift Serum")
                .append("category", "Kozmetik")
                .append("price", 480.00)
                .append("stock", 15)
                .append("imageName", "serum.jpg"));

            products.add(new Document("name", "Yüksek Bel Jean Pantolon")
                .append("category", "Tekstil")
                .append("price", 550.00)
                .append("stock", 8)
                .append("imageName", "pantolon.jpg"));

            products.add(new Document("name", "Nude Tonlu Far Paleti")
                .append("category", "Kozmetik")
                .append("price", 280.00)
                .append("stock", 2)
                .append("imageName", "palet.jpg"));

            products.add(new Document("name", "Desenli Yazlık Elbise")
                .append("category", "Tekstil")
                .append("price", 720.00)
                .append("stock", 10)
                .append("imageName", "elbise.jpg"));

            products.add(new Document("name", "Nemlendirici Dudak Yağı")
                .append("category", "Kozmetik")
                .append("price", 120.00)
                .append("stock", 30)
                .append("imageName", "dudak_yagi.jpg"));

            products.add(new Document("name", "Basic Beyaz Hoodie")
                .append("category", "Tekstil")
                .append("price", 420.00)
                .append("stock", 5)
                .append("imageName", "hoodie.jpg"));

            // Ürünleri ekle
            productsCollection.insertMany(products);

            System.out.println("✓ 10 ürün başarıyla Products koleksiyonuna eklendi!");
            System.out.println("✓ Toplam ürün sayısı: " + productsCollection.countDocuments());

            // === TEDARİKÇİLER ===
            List<Document> suppliers = new ArrayList<>();

            suppliers.add(new Document("companyName", "KozmoTrend Lojistik")
                .append("category", "Kozmetik")
                .append("contact", "0850 100 20 30"));

            suppliers.add(new Document("companyName", "Ege Tekstil Üretim")
                .append("category", "Tekstil")
                .append("contact", "0232 444 55 66"));

            suppliers.add(new Document("companyName", "Global Beauty Dağıtım")
                .append("category", "Kozmetik")
                .append("contact", "0212 555 44 33"));

            // Tedarikçileri ekle
            suppliersCollection.insertMany(suppliers);

            System.out.println("✓ 3 tedarikçi başarıyla Suppliers koleksiyonuna eklendi!");
            System.out.println("✓ Toplam tedarikçi sayısı: " + suppliersCollection.countDocuments());

            client.close();
        } catch (Exception e) {
            System.err.println("Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package com.trendyol.proje.gerekligereksiz;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class InsertProducts {
    public static void main(String[] args) {
        try {
            MongoClient client = MongoClients.create(
                "mongodb://adminuser:strongpassword123@localhost:27300/?authSource=admin"
            );
            MongoDatabase db = client.getDatabase("SmartTrendyolDB");
            MongoCollection<Document> productsCollection = db.getCollection("Products");



            // 10 örnek ürün
            List<Document> products = new ArrayList<>();

            products.add(new Document("name", "iPhone 15 Pro Max")
                .append("category", "Elektronik")
                .append("price", 85000.0)
                .append("stock", 45)
                .append("imagePath", "resimler/iphone15.jpg"));

            products.add(new Document("name", "Samsung Galaxy S24 Ultra")
                .append("category", "Elektronik")
                .append("price", 72000.0)
                .append("stock", 38)
                .append("imagePath", "resimler/samsung_s24.jpg"));

            products.add(new Document("name", "MacBook Pro 14 M3")
                .append("category", "Elektronik")
                .append("price", 125000.0)
                .append("stock", 22)
                .append("imagePath", "resimler/macbook.jpg"));

            products.add(new Document("name", "Sony WH-1000XM5 Kulaklık")
                .append("category", "Elektronik")
                .append("price", 12500.0)
                .append("stock", 67)
                .append("imagePath", "resimler/sony_headphones.jpg"));

            products.add(new Document("name", "Nike Air Max 270")
                .append("category", "Giyim")
                .append("price", 4500.0)
                .append("stock", 89)
                .append("imagePath", "resimler/nike_airmax.jpg"));

            products.add(new Document("name", "Adidas Ultraboost 23")
                .append("category", "Giyim")
                .append("price", 5200.0)
                .append("stock", 73)
                .append("imagePath", "resimler/adidas_ultraboost.jpg"));

            products.add(new Document("name", "Levi's 501 Jean")
                .append("category", "Giyim")
                .append("price", 2800.0)
                .append("stock", 156)
                .append("imagePath", "resimler/levis_jean.jpg"));

            products.add(new Document("name", "Dyson V15 Detect")
                .append("category", "Ev & Yaşam")
                .append("price", 35000.0)
                .append("stock", 31)
                .append("imagePath", "resimler/dyson_v15.jpg"));

            products.add(new Document("name", "Philips Airfryer XXL")
                .append("category", "Ev & Yaşam")
                .append("price", 9500.0)
                .append("stock", 54)
                .append("imagePath", "resimler/philips_airfryer.jpg"));

            products.add(new Document("name", "Logitech MX Master 3S")
                .append("category", "Elektronik")
                .append("price", 4800.0)
                .append("stock", 92)
                .append("imagePath", "resimler/logitech_mouse.jpg"));

            // Ürünleri ekle
            productsCollection.insertMany(products);

            System.out.println("✓ 10 ürün başarıyla eklendi!");
            System.out.println("✓ Toplam ürün sayısı: " + productsCollection.countDocuments());

            client.close();
        } catch (Exception e) {
            System.err.println("Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

package com.trendyol.proje;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.*;

public class DatabaseManager {
    private MongoDatabase db;

    public DatabaseManager() {
        String uri = "mongodb://adminuser:strongpassword123@localhost:27300/?authSource=admin";

        MongoClient client = MongoClients.create(uri);
        db = client.getDatabase("SmartTrendyolDB");
    }

    public List<Document> getProducts() {
        return db.getCollection("Products").find().into(new ArrayList<>());
    }

    public void addToBasket(Document product) {
        try {
            Document basketDoc = new Document(product);

            basketDoc.remove("_id");

            db.getCollection("Basket").insertOne(basketDoc);

            db.getCollection("Products").updateOne(
                    new Document("_id", product.getObjectId("_id")),
                    new Document("$inc", new Document("stock", -1))
            );

            System.out.println("Sepete eklendi ve stok düşüldü!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToFavorites(Document product) {
        try {
            Document favDoc = new Document(product);

            favDoc.remove("_id");

            db.getCollection("Favorites").insertOne(favDoc);
            System.out.println("Favorilere eklendi!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFromBasket(Document product) {
        try {
            db.getCollection("Basket").deleteOne(
                new Document("name", product.getString("name"))
                    .append("price", product.getDouble("price"))
            );
            System.out.println("Sepetten kaldırıldı!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFromFavorites(Document product) {
        try {
            db.getCollection("Favorites").deleteOne(
                new Document("name", product.getString("name"))
                    .append("price", product.getDouble("price"))
            );
            System.out.println("Favorilerden kaldırıldı!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Document> getSuppliers() {
        return db.getCollection("Suppliers").find().into(new ArrayList<>());
    }

    public List<Document> getFavorites() {
        return db.getCollection("Favorites").find().into(new ArrayList<>());
    }

    public List<Document> getBasket() {
        return db.getCollection("Basket").find().into(new ArrayList<>());
    }

    public String createOrder(String payMethod, String address, double total, String productDetails) {
        Document order = new Document("date", new java.util.Date())
                .append("total", total)
                .append("address", address)
                .append("paymentMethod", payMethod)
                .append("status", "Sipariş Alındı")
                .append("details", productDetails);

        var result = db.getCollection("OrderHistory").insertOne(order);
        db.getCollection("Basket").drop();
        
        return result.getInsertedId().asObjectId().getValue().toString();
    }

    public void updateProductStock(String productId, int newStock) {
        db.getCollection("Products").updateOne(
                new Document("_id", new org.bson.types.ObjectId(productId)),
                new Document("$set", new Document("stock", newStock))
        );
    }

    public List<Document> getOrderHistory() {
        return db.getCollection("OrderHistory").find().into(new ArrayList<>());
    }

    public boolean updateSpecificOrderStatus(String orderId, String status) {
        try {
            System.out.println("Updating order status - OrderID: " + orderId + ", Status: " + status);
            org.bson.types.ObjectId objectId = new org.bson.types.ObjectId(orderId);
            var result = db.getCollection("OrderHistory").updateOne(
                    new Document("_id", objectId),
                    new Document("$set", new Document("status", status))
            );
            System.out.println("Update result - Matched: " + result.getMatchedCount() + ", Modified: " + result.getModifiedCount());
            return result.getModifiedCount() > 0;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid ObjectId format: " + orderId);
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Error updating order status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
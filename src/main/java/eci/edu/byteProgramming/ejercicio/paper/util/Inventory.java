package eci.edu.byteProgramming.ejercicio.paper.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory implements PaymentObserver {
    private static final String LAPTOP001 = "LAPTOP001";
    private Map<String, Product> products;
    private Map<String, Integer> stock;

    public Inventory() {
        this.products = new ConcurrentHashMap<>();
        this.stock = new ConcurrentHashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        products.put(LAPTOP001, new Product(LAPTOP001, "Gaming Laptop", 1200.00, "Electronics"));
        products.put("PHONE001", new Product("PHONE001", "Smartphone", 800.00, "Electronics"));
        products.put("BOOK001", new Product("BOOK001", "Java Programming Book", 45.99, "Books"));

        stock.put(LAPTOP001, 5);
        stock.put("PHONE001", 10);
        stock.put("BOOK001", 20);
    }

    @Override
    public void onPaymentSuccess(PaymentMethod payment, String customerName,
                                  String customerEmail, String productId) {
        discountProduct(productId, 1);
    }

    @Override
    public void onPaymentFailed(PaymentMethod payment, String customerEmail) {
        System.out.println("Inventory: No stock changes, payment failed for " + customerEmail);
    }

    public boolean discountProduct(String productId, int quantity) {
        if (productId == null || quantity <= 0) {
            System.out.println("Inventory: Invalid productId or quantity.");
            return false;
        }

        if (stock.containsKey(productId) && stock.get(productId) >= quantity) {
            stock.compute(productId, (k, v) -> v - quantity);
            System.out.println("✅ Inventory: Discounted " + quantity + " units of " +
                    products.get(productId).getName());
            System.out.println("   Remaining stock: " + stock.get(productId));
            return true;
        } else {
            System.out.println("Inventory: Insufficient stock for " + productId);
            return false;
        }
    }

    public Product getProduct(String productId) {
        return products.get(productId);
    }

    public int getStock(String productId) {
        return stock.getOrDefault(productId, 0);
    }
}

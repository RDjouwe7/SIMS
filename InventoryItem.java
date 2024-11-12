package sims;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InventoryItem {
    private String name;
    private int quantity;
    private double price;
    private LocalDate expiryDate;

    public InventoryItem(String name, int quantity, double price, String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = LocalDate.parse(expiryDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getName() {
        return name;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    @Override
    public String toString() {
        return String.format("Item: %s | Quantity: %d | Price: %.2f | Expiry Date: %s",
                name, quantity, price, expiryDate);
    }
}

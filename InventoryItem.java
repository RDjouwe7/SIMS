import java.io.Serializable;

public class InventoryItem implements Serializable {
    private String name;
    private int quantity;
    private double price;
    private String expiryDate;

    public InventoryItem(String name, int quantity, double price, String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    // Setters (for updating item details)
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Item: " + name + " | Quantity: " + quantity + " | Price: $" + price + " | Expiry Date: " + expiryDate;
    }
}

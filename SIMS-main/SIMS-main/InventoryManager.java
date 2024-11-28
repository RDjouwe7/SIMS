import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, InventoryItem> inventory;
    private static final String FILE_PATH = "inventory_data.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InventoryManager() {
        this.inventory = new HashMap<>();
        loadInventoryFromFile();
    }

    // Check if an item exists in inventory
    public boolean itemExists(String name) {
        return inventory.containsKey(name);
    }

    // Update item quantity and save changes
    public void updateQuantity(String name, int quantity) {
        InventoryItem item = inventory.get(name);
        if (item != null) {
            item.setQuantity(quantity);
            saveInventoryToFile();
        }
    }

    // Update item price and save changes
    public void updatePrice(String name, double price) {
        InventoryItem item = inventory.get(name);
        if (item != null) {
            item.setPrice(price);
            saveInventoryToFile();
        }
    }

    // Update item expiry date and save changes
    public void updateExpiryDate(String name, String expiryDate) {
        InventoryItem item = inventory.get(name);
        if (item != null) {
            item.setExpiryDate(expiryDate);
            saveInventoryToFile();
        }
    }

    // Delete an item from inventory and save changes
    public boolean deleteItem(String name) {
        if (inventory.remove(name) != null) {
            saveInventoryToFile();
            return true;
        }
        return false;
    }

    // Add a new item to inventory and save it
    public void addItem(String name, int quantity, double price, String expiryDate) {
        InventoryItem item = new InventoryItem(name, quantity, price, expiryDate);
        inventory.put(name, item);
        saveInventoryToFile();
    }

    // List all items in the inventory
    public void listItems() {
        System.out.println("\n--- Inventory List ---");
        for (InventoryItem item : inventory.values()) {
            System.out.println(item);
        }
    }

    // Check for items close to expiration
    public void checkExpirations() {
        System.out.println("\n--- Expiration Check ---");
        LocalDate today = LocalDate.now();

        for (InventoryItem item : inventory.values()) {
            try {
                // Parse expiry date string to LocalDate
                LocalDate expiryDate = LocalDate.parse(item.getExpiryDate(), DATE_FORMATTER);
                long daysToExpire = ChronoUnit.DAYS.between(today, expiryDate);

                if (daysToExpire <= 7) {
                    System.out.printf("Item '%s' is expiring soon! Expiry Date: %s (in %d days)\n",
                            item.getName(), item.getExpiryDate(), daysToExpire);
                }
            } catch (Exception e) {
                System.out.println("Error parsing expiry date for item " + item.getName() + ": " + e.getMessage());
            }
        }
    }

    // Save inventory to file
    private void saveInventoryToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(inventory);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory data: " + e.getMessage());
        }
    }

    // Load inventory from file
    @SuppressWarnings("unchecked")
    private void loadInventoryFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            inventory = (Map<String, InventoryItem>) ois.readObject();
            System.out.println("Inventory loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous inventory data found. Starting fresh.");
        }
    }

    // Placeholder for report generation
    public void generateReport() {
        // Implementation can be added later
        System.out.println("Generating report...");
    }
}

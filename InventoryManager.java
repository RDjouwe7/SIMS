package sims;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class InventoryManager {
    private Map<String, InventoryItem> inventory;

    public InventoryManager() {
        this.inventory = new HashMap<>();
    }

    public void addItem(String name, int quantity, double price, String expiryDate) {
        InventoryItem item = new InventoryItem(expiryDate, quantity, price, expiryDate);
        inventory.put(name, item);
    }

    public void listItems() {
        System.out.println("\n--- Inventory List ---");
        for (InventoryItem item : inventory.values()) {
            System.out.println(item);
        }
    }

    public void checkExpirations() {
        System.out.println("\n--- Expiration Check ---");
        LocalDate today = LocalDate.now();
        for (InventoryItem item : inventory.values()) {
            long daysToExpire = ChronoUnit.DAYS.between(today, item.getExpiryDate());
            if (daysToExpire <= 7) {
                System.out.printf("Item '%s' is expiring soon! Expiry Date: %s (in %d days)\n",
                        item.getName(), item.getExpiryDate(), daysToExpire);
            }
        }
    }

    public void generateReport() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateReport'");
    }
}


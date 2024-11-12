package sims;

import java.util.InputMismatchException;
import java.util.Scanner;

public class menuClass {
    private InventoryManager inventoryManager;
    private Scanner scanner;

    public menuClass() {
        this.inventoryManager = new InventoryManager();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n--- Smart Inventory Menu ---");
            System.out.println("1. Add New Item");
            System.out.println("2. Update Item");
            System.out.println("3. Delete Item");
            System.out.println("4. List Items");
            System.out.println("5. Check Expiration Dates");
            System.out.println("6. Generate Report");
            System.out.println("0. Exit");

            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
                continue;
            }

            switch (choice) {
                case 1: addItem(); break;
                case 2: updateItem(); break;
                case 3: deleteItem(); break;
                case 4: listItems(); break;
                case 5: checkExpiration(); break;
                case 6: generateReport(); break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addItem() {
        System.out.print("Enter item name: ");
        scanner.nextLine(); // Clear newline character from buffer
        String name = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter expiration date (yyyy-mm-dd): ");
        String expiryDate = scanner.next();

        inventoryManager.addItem(name, quantity, price, expiryDate);
        System.out.println("Item added successfully.");
    }

    private void updateItem() {
        // Implementation for updating item
    }

    private void deleteItem() {
        // Implementation for deleting item
    }

    private void listItems() {
        inventoryManager.listItems();
    }

    private void checkExpiration() {
        inventoryManager.checkExpirations();
    }

    private void generateReport() {
        inventoryManager.generateReport();
    }
}


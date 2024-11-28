import java.util.InputMismatchException;
import java.util.Scanner;

public class menuClass {
    private InventoryManager inventoryManager;
    private Scanner scanner;
    private loginMenu login;

    public menuClass() {
        this.inventoryManager = new InventoryManager();
        this.scanner = new Scanner(System.in);
        this.login = new loginMenu();
    }

    public void displayMenu() {
        System.out.println("Welcome to the Smart Inventory Management System!");
        System.out.println("Please log in to continue.");

        if (!login.authenticate()) {
            System.out.println("Exiting system.");
            return;
        }

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
                scanner.next();
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
        scanner.nextLine();
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
        System.out.print("Enter the name of the item to update: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();

        if (!inventoryManager.itemExists(name)) {
            System.out.println("Item not found.");
            return;
        }

        System.out.println("What would you like to update?");
        System.out.println("1. Quantity");
        System.out.println("2. Price");
        System.out.println("3. Expiration Date");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter new quantity: ");
                int newQuantity = scanner.nextInt();
                inventoryManager.updateQuantity(name, newQuantity);
                System.out.println("Quantity updated successfully.");
                break;
            case 2:
                System.out.print("Enter new price: ");
                double newPrice = scanner.nextDouble();
                inventoryManager.updatePrice(name, newPrice);
                System.out.println("Price updated successfully.");
                break;
            case 3:
                System.out.print("Enter new expiration date (yyyy-mm-dd): ");
                scanner.nextLine();
                String newExpiryDate = scanner.nextLine();
                inventoryManager.updateExpiryDate(name, newExpiryDate);
                System.out.println("Expiration date updated successfully.");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void deleteItem() {
        System.out.print("Enter the name of the item to delete: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();

        if (inventoryManager.deleteItem(name)) {
            System.out.println("Item deleted successfully.");
        } else {
            System.out.println("Item not found.");
        }
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
    
    public static void main(String[] args) {
        menuClass caller = new menuClass();
        caller.displayMenu();
    }
}

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckExpiration {

    private static final String FILE_PATH = "inventorydata.txt"; // The file where stock data is stored

    public static void checkExpiration() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            List<String> expirationWarnings = new ArrayList<>();
            List<String> restockWarnings = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                
                if (data.length < 4) {
                    continue; 
                }

                String name = data[0].trim();
                int quantity;
                //double price;
                String expiryDateString = data[3].trim();

                try {
                    quantity = Integer.parseInt(data[1].trim());
                    //price = Double.parseDouble(data[2].trim());
                } catch (NumberFormatException e) {
                    // Handle any parsing error and skip this line if data is invalid
                    System.err.println("Skipping invalid data: " + line);
                    continue;
                }

                // Check if the item is close to expiration
                LocalDate expiryDate;
                try {
                    expiryDate = LocalDate.parse(expiryDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } catch (Exception e) {
                    // If the date parsing fails, skip the item
                    System.err.println("Skipping item with invalid expiry date: " + name);
                    continue;
                }

                LocalDate currentDate = LocalDate.now();
                LocalDate expirationThreshold = currentDate.plusDays(3); // Expiration threshold set to 3 days

                if (expiryDate.isBefore(expirationThreshold) || expiryDate.isEqual(expirationThreshold)) {
                    expirationWarnings.add("Item: '" + name + "', Expiry Date: " + expiryDate);
                }

                // Check if the item quantity is less than or equal to 10
                if (quantity <= 10) {
                    restockWarnings.add("Item: '" + name + "', Current Quantity: " + quantity);
                }
            }

            
            if (!expirationWarnings.isEmpty()) {
                ImageIcon expiredIcon = new ImageIcon("expired.png"); 
                JOptionPane.showMessageDialog(
                        null,
                        "The following items are nearing their expiration date:\n" + String.join("\n", expirationWarnings),
                        "Expiration Warning",
                        JOptionPane.INFORMATION_MESSAGE,
                        expiredIcon
                );
            }


            if (!restockWarnings.isEmpty()) {
                ImageIcon restockIcon = new ImageIcon("restock.png");
                JOptionPane.showMessageDialog(
                        null,
                        "The following items have low stock:\n" + String.join("\n", restockWarnings),
                        "Restock Alert",
                        JOptionPane.INFORMATION_MESSAGE,
                        restockIcon
                );
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading inventory data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        checkExpiration();
    }
}

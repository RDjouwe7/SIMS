import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CheckExpiration {

    private static final String FILE_PATH = "inventorydata.txt"; // The file where stock data is stored

    public static void checkExpiration() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean isExpirationClose = false;
            boolean isQuantityLow = false;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                int quantity = Integer.parseInt(data[1]);
                @SuppressWarnings("unused")
                double price = Double.parseDouble(data[2]);
                String expiryDateString = data[3];

                // Check if the item is close to expiration
                LocalDate expiryDate = LocalDate.parse(expiryDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate currentDate = LocalDate.now();
                LocalDate expirationThreshold = currentDate.plusDays(3); // Expiration threshold set to 3 days

                if (expiryDate.isBefore(expirationThreshold) || expiryDate.isEqual(expirationThreshold)) {
                    isExpirationClose = true;
                }

                // Check if the item quantity is less than 10
                if (quantity < 10) {
                    isQuantityLow = true;
                }

                // Display alert for expiration or low quantity
                if (isExpirationClose) {
                    JOptionPane.showMessageDialog(null, "The item '" + name + "' is close to expiration! Expiry Date: " + expiryDate);
                    isExpirationClose = false; // Reset the expiration flag after showing the message
                }

                if (isQuantityLow) {
                    JOptionPane.showMessageDialog(null, "The item '" + name + "' has low stock! Quantity: " + quantity);
                    isQuantityLow = false; // Reset the quantity flag after showing the message
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading inventory data: " + e.getMessage());
        }
    }
}

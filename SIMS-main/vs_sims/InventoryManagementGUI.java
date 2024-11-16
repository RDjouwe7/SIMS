import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InventoryManagementGUI extends JFrame {
    private JTextField nameField, quantityField, priceField, expiryDateField;
    private JButton saveButton, returnButton;
    private static final String FILE_PATH = "inventorydata.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InventoryManagementGUI() {
        // Set up the frame
        setTitle("Inventory Management");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Close only the Inventory window
        setLocationRelativeTo(null);  // Center the window
        setLayout(new BorderLayout());

        // Create a panel for the form fields
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));  // 5 rows, 2 columns
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add input fields
        formPanel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        formPanel.add(new JLabel("Expiry Date (yyyy-MM-dd):"));
        expiryDateField = new JTextField();
        formPanel.add(expiryDateField);

        // Add the form panel to the center of the frame
        add(formPanel, BorderLayout.CENTER);

        // Add the buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Save button
        saveButton = new JButton("Save Item");
        buttonPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveItem();
            }
        });

        // Return button
        returnButton = new JButton("Return to Main Menu");
        buttonPanel.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });

        // Add the button panel to the south of the frame
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void saveItem() {
        // Input validation
        String name = nameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String expiryDate = expiryDateField.getText();

        if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty() || expiryDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);
            LocalDate.parse(expiryDate, DATE_FORMATTER);  // Validate the expiry date format

            // Save the data to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(name + "," + quantity + "," + price + "," + expiryDate);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Item saved successfully!");
                clearFields();
            }

        } catch (NumberFormatException | IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving item: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private void clearFields() {
        // Clear the input fields after saving
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        expiryDateField.setText("");
    }

    private void returnToMainMenu() {
        // Close the current window and open the Main Menu
        dispose();
        new MainMenuGUI().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InventoryManagementGUI().setVisible(true);
        });
    }
}

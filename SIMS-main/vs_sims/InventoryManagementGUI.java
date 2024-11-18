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
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen mode
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the Inventory window
        setLayout(new BorderLayout());

        // Create a panel for the form fields
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Add input fields
        formPanel.add(new JLabel("Item Name:", JLabel.RIGHT));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Quantity:", JLabel.RIGHT));
        quantityField = new JTextField();
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Price:", JLabel.RIGHT));
        priceField = new JTextField();
        formPanel.add(priceField);

        formPanel.add(new JLabel("Expiry Date (yyyy-MM-dd):", JLabel.RIGHT));
        expiryDateField = new JTextField();
        formPanel.add(expiryDateField);

        add(formPanel, BorderLayout.CENTER);

        // Add the buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        saveButton = new JButton("Save Item");
        buttonPanel.add(saveButton);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveItem();
            }
        });

        returnButton = new JButton("Return to Main Menu");
        buttonPanel.add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });

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
            LocalDate.parse(expiryDate, DATE_FORMATTER); // Validate the expiry date format

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
        nameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        expiryDateField.setText("");
    }

    private void returnToMainMenu() {
        dispose();
        new MainMenuGUI().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InventoryManagementGUI().setVisible(true);
        });
    }
}

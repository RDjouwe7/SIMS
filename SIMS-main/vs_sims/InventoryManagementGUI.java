import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InventoryManagementGUI extends JFrame {
    private JTextField nameField, quantityField, priceField, expiryDateField;
    private JComboBox<String> categoryComboBox;
    private JButton saveButton, returnButton;
    private static final String FILE_PATH = "inventorydata.txt";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public InventoryManagementGUI() {
        // Set frame properties
        ImageIcon icon = new ImageIcon("mainlogo.png");
        setIconImage(icon.getImage());
        setTitle("Inventory Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // We are disposing this frame
        setSize(800, 600);
        setResizable(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Adding a window listener to call the Main Menu on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                returnToMainMenu();
            }
        });

        // Creating a form panel for the form fields with GridBagLayout (CENTER)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // we need GridBagConstraints to keep everything in like a box
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Add space between components
        gbc.anchor = GridBagConstraints.WEST; // Align labels to the left
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make text fields expand to fill available space

        // Add input fields with labels
        formPanel.add(new JLabel("Item Name:"), gbc);
        gbc.gridx = 1; // Move to the next column for input field
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(300, 30)); // Set preferred size
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; // Back to the first column for the next label
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(300, 30)); // Set preferred size
        formPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(300, 30)); // Set preferred size
        formPanel.add(priceField, gbc);

        gbc.gridx = 0;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        String[] categories = {"Food", "Non-Food"};
        categoryComboBox = new JComboBox<>(categories);
        formPanel.add(categoryComboBox, gbc);

        gbc.gridx = 0;
        formPanel.add(new JLabel("Expiry Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        expiryDateField = new JTextField();
        expiryDateField.setPreferredSize(new Dimension(300, 30)); // Set preferred size
        formPanel.add(expiryDateField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Add the buttons panel (SOUTH)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setPreferredSize(new Dimension(getWidth(), 60)); // Make button panel span the width of the window

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
        // User_Input checker
        String name = nameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();
        String expiryDate = expiryDateField.getText();
        String category = (String) categoryComboBox.getSelectedItem();
    
        if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
    
        try {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);
    
            // If the item is food, check for expiry date
            if (category.equals("Food") && expiryDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an expiry date for food items.");
                return;
            }
    
            // If the item is food, validate the expiry date format
            if (category.equals("Food")) {
                try {
                    LocalDate parsedExpiryDate = LocalDate.parse(expiryDate, DATE_FORMATTER); // Validate the expiry date format
    
                    // Check if the expiry date is in the past
                    if (parsedExpiryDate.isBefore(LocalDate.now())) {
                        JOptionPane.showMessageDialog(this, "The expiry date cannot be in the past.");
                        return;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Invalid date format. Please use yyyy-MM-dd.");
                    return;
                }
            }
    
            // Load existing items to check for duplicates
            boolean itemExists = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data[0].equalsIgnoreCase(name)) { // Check for case-insensitive match
                        itemExists = true;
                        break;
                    }
                }
            }
    
            if (itemExists) {
                JOptionPane.showMessageDialog(this, "The item '" + name + "' already exists in the inventory.");
                return;
            }
    
            // Saving the data to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(name + "," + quantity + "," + price + "," + (category.equals("Food") ? expiryDate : "N/A"));
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Item saved successfully!");
                clearFields();
            }
    
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for quantity and price.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving item: " + e.getMessage());
        }
    }
    
    // We need to empty the fields too
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

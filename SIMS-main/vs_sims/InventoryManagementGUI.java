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
        // Set frame properties
                    // Setting the Icon, Title and sizes
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

        // Creating a  form panel for the form fields with GridBagLayout (CENTER)
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

        gbc.gridx = 0; //Back to the first column for the next label
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

        if (name.isEmpty() || quantityText.isEmpty() || priceText.isEmpty() || expiryDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);
            LocalDate.parse(expiryDate, DATE_FORMATTER); // Validate the expiry date format

            // Saving the data to the file
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
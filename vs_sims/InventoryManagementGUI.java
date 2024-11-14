import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InventoryManagementGUI extends JFrame {
    private StockManagementGUI stockManagementGUI; // Reference to StockManagementGUI

    public InventoryManagementGUI(ArrayList<Item> items) {
        setTitle("Inventory Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize StockManagementGUI with the shared items list
        stockManagementGUI = new StockManagementGUI(items);

        // Add a button to add items
        JButton addItemButton = new JButton("Add New Item");
        addItemButton.addActionListener(e -> openAddItemDialog());
        add(addItemButton, BorderLayout.CENTER);

        setVisible(true);
    }

    private void openAddItemDialog() {
        // Dialog for adding a new item
        JTextField categoryField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField amountField = new JTextField();
        JTextField expirationDateField = new JTextField(); // Optional: MM/DD/YYYY format

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Expiration Date (MM/DD/YYYY):"));
        panel.add(expirationDateField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Item", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String category = categoryField.getText();
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int amount = Integer.parseInt(amountField.getText());

                Date expirationDate = null;
                if (!expirationDateField.getText().isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    expirationDate = sdf.parse(expirationDateField.getText());
                }

                // Create the new item
                Item newItem = new Item(category, name, price, amount, expirationDate);

                // Add the item to StockManagementGUI
                stockManagementGUI.addItem(newItem);

                JOptionPane.showMessageDialog(this, "Item added successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your data and try again.");
            }
        }
    }
}

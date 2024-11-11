import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StockManagementGUI extends JFrame {
    private ArrayList<Item> items;
    private DefaultTableModel tableModel;
    private JTable table;

    public StockManagementGUI(ArrayList<Item> sharedItems) {
        this.items = sharedItems;

        setTitle("Stock Management");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 10, 10));

        // Price
        JLabel priceLabel = new JLabel("New Price:");
        panel.add(priceLabel);

        JTextField priceField = new JTextField();
        panel.add(priceField);

        // Amount
        JLabel amountLabel = new JLabel("New Amount:");
        panel.add(amountLabel);

        JTextField amountField = new JTextField();
        panel.add(amountField);

        // Expiration Date
        JLabel expirationDateLabel = new JLabel("New Expiration Date (MM/DD/YYYY):");
        panel.add(expirationDateLabel);

        JTextField expirationDateField = new JTextField();
        panel.add(expirationDateField);

        // Update Item button
        JButton updateButton = new JButton("Update Item");
        updateButton.addActionListener(e -> updateItem(priceField, amountField, expirationDateField));
        panel.add(updateButton);

        // Table for displaying the inventory
        String[] columnNames = {"Category", "Item Name", "Price", "Amount", "Expiration Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        // Delete Item button
        JButton deleteButton = new JButton("Delete Item");
        deleteButton.addActionListener(e -> deleteItem());
        panel.add(deleteButton);

        // Back to Main Menu button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> backToMainMenu());
        panel.add(backButton);

        // Populate the table with the inventory data
        populateTable();

        add(panel);
        setVisible(true);
    }

    private void populateTable() {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Populate the table with the inventory data
        for (Item item : items) {
            Object[] rowData = {
                item.getCategory(),
                item.getName(),
                item.getPrice(),
                item.getAmount(),
                formatExpirationDate(item.getExpirationDate())
            };
            tableModel.addRow(rowData);
        }
    }

    private String formatExpirationDate(Date expirationDate) {
        if (expirationDate != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            return sdf.format(expirationDate);
        }
        return "";
    }

    private void updateItem(JTextField priceField, JTextField amountField, JTextField expirationDateField) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String itemName = (String) table.getValueAt(selectedRow, 1);
            Item itemToUpdate = null;

            // Find the selected item
            for (Item item : items) {
                if (item.getName().equals(itemName)) {
                    itemToUpdate = item;
                    break;
                }
            }

            if (itemToUpdate != null) {
                String priceText = priceField.getText();
                String amountText = amountField.getText();
                String expirationDateText = expirationDateField.getText();

                if (!priceText.isEmpty()) {
                    try {
                        double newPrice = Double.parseDouble(priceText);
                        itemToUpdate.setPrice(newPrice);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid price input.");
                        return;
                    }
                }

                if (!amountText.isEmpty()) {
                    try {
                        int newAmount = Integer.parseInt(amountText);
                        itemToUpdate.setAmount(newAmount);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid amount input.");
                        return;
                    }
                }

                if (!expirationDateText.isEmpty()) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Date newExpirationDate = sdf.parse(expirationDateText);
                        itemToUpdate.setExpirationDate(newExpirationDate);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Invalid expiration date format.");
                        return;
                    }
                }

                // Update the table to reflect changes
                populateTable();
                JOptionPane.showMessageDialog(this, "Item updated successfully.");
            }
        }
    }

    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String itemName = (String) table.getValueAt(selectedRow, 1);

            // Find and remove the item from the list
            items.removeIf(item -> item.getName().equals(itemName));

            // Remove the row from the table
            tableModel.removeRow(selectedRow);

            JOptionPane.showMessageDialog(this, "Item deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.");
        }
    }

    private void backToMainMenu() {
        // Close the current window and go back to the main menu
        dispose();
        new MainMenuGUI(); // Assuming you have a MainMenuGUI class
    }
}

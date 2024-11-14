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
    private JTextField searchField;

    public StockManagementGUI(ArrayList<Item> sharedItems) {
        this.items = sharedItems;

        setTitle("Stock Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Use a BorderLayout for better component placement
        setLayout(new BorderLayout(10, 10));

        // Panel for managing item updates and search
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createTitledBorder("Item Management"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add space between components

        // Search Section
        JLabel searchLabel = new JLabel("Search Item:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(searchLabel, gbc);

        searchField = new JTextField(15);
        gbc.gridx = 1;
        controlPanel.add(searchField, gbc);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchItem());
        gbc.gridx = 2;
        controlPanel.add(searchButton, gbc);

        // Price Section
        JLabel priceLabel = new JLabel("New Price:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(priceLabel, gbc);

        JTextField priceField = new JTextField(15);
        gbc.gridx = 1;
        controlPanel.add(priceField, gbc);

        // Amount Section
        JLabel amountLabel = new JLabel("New Amount:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(amountLabel, gbc);

        JTextField amountField = new JTextField(15);
        gbc.gridx = 1;
        controlPanel.add(amountField, gbc);

        // Expiration Date Section
        JLabel expirationDateLabel = new JLabel("Expiration Date (MM/DD/YYYY):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        controlPanel.add(expirationDateLabel, gbc);

        JTextField expirationDateField = new JTextField(15);
        gbc.gridx = 1;
        controlPanel.add(expirationDateField, gbc);

        // Update Item button
        JButton updateButton = new JButton("Update Item");
        updateButton.addActionListener(e -> updateItem(priceField, amountField, expirationDateField));
        gbc.gridx = 2;
        controlPanel.add(updateButton, gbc);

        // Add control panel to the frame
        add(controlPanel, BorderLayout.NORTH);

        // Table for displaying the inventory
        String[] columnNames = {"Category", "Item Name", "Price", "Amount", "Expiration Date"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel for Delete and Back options
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton deleteButton = new JButton("Delete Item");
        deleteButton.addActionListener(e -> deleteItem());
        buttonPanel.add(deleteButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> backToMainMenu());
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Populate the table with the inventory data
        populateTable();

        setVisible(true);
    }

    private void populateTable() {
        tableModel.setRowCount(0);
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

            for (Item item : items) {
                if (item.getName().equals(itemName)) {
                    itemToUpdate = item;
                    break;
                }
            }

            if (itemToUpdate != null) {
                try {
                    if (!priceField.getText().isEmpty()) {
                        itemToUpdate.setPrice(Double.parseDouble(priceField.getText()));
                    }
                    if (!amountField.getText().isEmpty()) {
                        itemToUpdate.setAmount(Integer.parseInt(amountField.getText()));
                    }
                    if (!expirationDateField.getText().isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        itemToUpdate.setExpirationDate(sdf.parse(expirationDateField.getText()));
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error in updating item details. Check inputs.");
                    return;
                }

                populateTable();
                JOptionPane.showMessageDialog(this, "Item updated successfully.");
            }
        }
    }

    private void deleteItem() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String itemName = (String) table.getValueAt(selectedRow, 1);
            items.removeIf(item -> item.getName().equals(itemName));
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Item deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.");
        }
    }

    public void addItem(Item item) {
        items.add(item); // Add the new item to the list
        populateTable(); // Refresh the table to display the new item
        table.revalidate();
        table.repaint();
    }

    private void backToMainMenu() {
        dispose();
        new MainMenuGUI(); // Assuming you have a MainMenuGUI class
    }

    private void searchItem() {
        String searchQuery = searchField.getText().trim().toLowerCase();
        if (searchQuery.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter an item name to search.");
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String itemName = tableModel.getValueAt(i, 1).toString().toLowerCase();
            if (itemName.equals(searchQuery)) {
                table.setRowSelectionInterval(i, i);
                table.scrollRectToVisible(table.getCellRect(i, 0, true));
                JOptionPane.showMessageDialog(this, "Item found: " + itemName);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Item not found.");
    }

    public static void main(String[] args) {
        // Sample initialization for testing
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Category1", "Item1", 100.0, 10, new Date()));
        new StockManagementGUI(items);
    }
}

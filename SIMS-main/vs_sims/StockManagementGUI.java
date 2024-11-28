import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StockManagementGUI extends JFrame {
    private JTable stockTable;
    private JButton returnButton, updateButton, deleteButton, searchButton, sortButton;
    private static final String FILE_PATH = "inventorydata.txt";
    private List<String[]> stockData;

    public StockManagementGUI() {
        // Set frame properties
                    // Setting the Icon, Title and sizes
        ImageIcon icon = new ImageIcon("mainlogo.png");
        setIconImage(icon.getImage());
        setTitle("Stock Management");
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Make the frame full screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Dispose on close instead of exit
        setLocationRelativeTo(null);  // Center the window
        setLayout(new BorderLayout());

        // Adding a window listener to call the Main Menu on close
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                returnToMainMenu(); 
            }
        });
        // Creating the stock table with column headers
        stockTable = new JTable(new DefaultTableModel(new String[]{"Name", "Quantity", "Price", "Expiry Date"}, 0));
        add(new JScrollPane(stockTable), BorderLayout.CENTER); 

        // Create buttons
        JPanel buttonPanel = new JPanel();
        returnButton = new JButton("Return to Main Menu");
        updateButton = new JButton("Update Item");
        deleteButton = new JButton("Delete Item");
        searchButton = new JButton("Search Item");
        sortButton = new JButton("Sort Items"); // New Sort button

        buttonPanel.add(returnButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        returnButton.addActionListener(e -> returnToMainMenu());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());
        searchButton.addActionListener(e -> searchItem());
        sortButton.addActionListener(e -> sortItems()); // Add sort action

        // Load stock data into the table
        loadStockData();
    }

    private void loadStockData() {
        DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
        stockData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(new Object[]{data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2]), data[3]});
                stockData.add(data);  // Store stock data in the list for easier manipulation
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading stock data: " + e.getMessage());
        }
    }

    private void returnToMainMenu() {
        dispose();
        new MainMenuGUI().setVisible(true);
    }

    private void updateItem() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow >= 0) {
            String oldName = (String) stockTable.getValueAt(selectedRow, 0);
            String oldQuantity = stockTable.getValueAt(selectedRow, 1).toString();
            String oldPrice = stockTable.getValueAt(selectedRow, 2).toString();
            String oldExpiry = (String) stockTable.getValueAt(selectedRow, 3);

            String name = JOptionPane.showInputDialog(this, "Enter new name:", oldName);
            String quantity = JOptionPane.showInputDialog(this, "Enter new quantity:", oldQuantity);
            String price = JOptionPane.showInputDialog(this, "Enter new price:", oldPrice);
            String expiryDate = JOptionPane.showInputDialog(this, "Enter new expiry date (yyyy-MM-dd):", oldExpiry);

            stockTable.setValueAt(name, selectedRow, 0);
            stockTable.setValueAt(Integer.parseInt(quantity), selectedRow, 1);
            stockTable.setValueAt(Double.parseDouble(price), selectedRow, 2);
            stockTable.setValueAt(expiryDate, selectedRow, 3);

            stockData.set(selectedRow, new String[]{name, quantity, price, expiryDate});
            saveStockData();
            JOptionPane.showMessageDialog(this, "Item updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to update.");
        }
    }

    private void deleteItem() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow >= 0) {
            DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
            model.removeRow(selectedRow);
            stockData.remove(selectedRow);
            saveStockData();
            JOptionPane.showMessageDialog(this, "Item deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.");
        }
    }

    private void saveStockData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] item : stockData) {
                writer.write(String.join(",", item));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving stock data: " + e.getMessage());
        }
    }

    private void searchItem() {
        String query = JOptionPane.showInputDialog(this, "Enter item name to search:");
        if (query == null || query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Search query cannot be empty.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
        boolean found = false;

        for (int i = 0; i < model.getRowCount(); i++) {
            String itemName = model.getValueAt(i, 0).toString();
            if (itemName.equalsIgnoreCase(query)) {
                stockTable.setRowSelectionInterval(i, i);  // Highlight the row
                stockTable.scrollRectToVisible(stockTable.getCellRect(i, 0, true));  // Scroll to the row
                found = true;
                break;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Item not found.");
        }
    }

    private void sortItems() {
        // Ask the user for sorting criteria
        String[] options = {"Name", "Quantity", "Price", "Expiry Date"};
        String choice = (String) JOptionPane.showInputDialog(
            this, 
            "Choose a column to sort by:", 
            "Sort Options", 
            JOptionPane.PLAIN_MESSAGE, 
            null, 
            options, 
            options[0]
        );

        if (choice == null) return;

        // Comparator based on user choice
        Comparator<String[]> comparator;
        switch (choice) {
            case "Quantity":
                comparator = Comparator.comparingInt(o -> Integer.parseInt(o[1]));
                break;
            case "Price":
                comparator = Comparator.comparingDouble(o -> Double.parseDouble(o[2]));
                break;
            case "Expiry Date":
                comparator = Comparator.comparing(o -> o[3]);
                break;
            case "Name":
            default:
                comparator = Comparator.comparing(o -> o[0].toLowerCase());
        }

        // Sort the stock data
        Collections.sort(stockData, comparator);

        // Update the table
        DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
        model.setRowCount(0); // Clear the table
        for (String[] row : stockData) {
            model.addRow(new Object[]{row[0], Integer.parseInt(row[1]), Double.parseDouble(row[2]), row[3]});
        }

        JOptionPane.showMessageDialog(this, "Items sorted by " + choice + "!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StockManagementGUI().setVisible(true);
        });
    }
}

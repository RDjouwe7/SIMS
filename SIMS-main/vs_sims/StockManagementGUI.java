import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StockManagementGUI extends JFrame {
    private JTable stockTable;
    private JButton returnButton, updateButton, deleteButton;
    private static final String FILE_PATH = "inventorydata.txt";
    private List<String[]> stockData;

    public StockManagementGUI() {
        // Set up the frame to be full screen
        setTitle("Stock Management");
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Make the frame full screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Dispose on close instead of exit
        setLocationRelativeTo(null);  // Center the window
        setLayout(new BorderLayout());

        // Create the stock table with column headers
        stockTable = new JTable(new DefaultTableModel(new String[]{"Name", "Quantity", "Price", "Expiry Date"}, 0));
        add(new JScrollPane(stockTable), BorderLayout.CENTER);  // Add the table to the center

        // Create buttons
        JPanel buttonPanel = new JPanel();
        returnButton = new JButton("Return to Main Menu");
        updateButton = new JButton("Update Item");
        deleteButton = new JButton("Delete Item");

        buttonPanel.add(returnButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        returnButton.addActionListener(e -> returnToMainMenu());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());

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
        // Dispose the current window and show the main menu (assumes a MainMenuGUI class)
        dispose();
        new MainMenuGUI().setVisible(true);  // Make sure MainMenuGUI exists and is implemented
    }

    private void updateItem() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Get the selected item details
            String oldName = (String) stockTable.getValueAt(selectedRow, 0);
            String oldQuantity = stockTable.getValueAt(selectedRow, 1).toString();
            String oldPrice = stockTable.getValueAt(selectedRow, 2).toString();
            String oldExpiry = (String) stockTable.getValueAt(selectedRow, 3);

            // Ask user for new values using input dialogs
            String name = JOptionPane.showInputDialog(this, "Enter new name:", oldName);
            String quantity = JOptionPane.showInputDialog(this, "Enter new quantity:", oldQuantity);
            String price = JOptionPane.showInputDialog(this, "Enter new price:", oldPrice);
            String expiryDate = JOptionPane.showInputDialog(this, "Enter new expiry date (yyyy-MM-dd):", oldExpiry);

            // Update the table
            stockTable.setValueAt(name, selectedRow, 0);
            stockTable.setValueAt(Integer.parseInt(quantity), selectedRow, 1);
            stockTable.setValueAt(Double.parseDouble(price), selectedRow, 2);
            stockTable.setValueAt(expiryDate, selectedRow, 3);

            // Update the stockData list
            stockData.set(selectedRow, new String[]{name, quantity, price, expiryDate});

            // Save updated data back to the file
            saveStockData();
            JOptionPane.showMessageDialog(this, "Item updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to update.");
        }
    }

    private void deleteItem() {
        int selectedRow = stockTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Remove the selected row from the table
            DefaultTableModel model = (DefaultTableModel) stockTable.getModel();
            model.removeRow(selectedRow);

            // Remove the item from the stockData list
            stockData.remove(selectedRow);

            // Save updated data back to the file
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StockManagementGUI().setVisible(true);
        });
    }
}

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DailyReportGUI extends JFrame {
    private JTable salesTable;
    private JButton addButton, saveButton, refreshButton, backButton;
    private JLabel totalRevenueLabel;
    private double totalRevenue = 0;
    private static final String FILE_PATH = "inventorydata.txt";
    private static final String REPORT_FILE = "dailyreport.txt";
    private List<String[]> stockData;

    public DailyReportGUI() {
        // Set up the frame to be full screen
        setTitle("Daily Sales Report");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this frame only
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Create the sales table with column headers
        salesTable = new JTable(new DefaultTableModel(new String[]{"Item Name", "Quantity Sold", "Price per Unit", "Total Price"}, 0));
        add(new JScrollPane(salesTable), BorderLayout.CENTER); // Add the table to the center

        // Panel for total revenue and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Total revenue label
        totalRevenueLabel = new JLabel("Total Revenue: $0.00");
        totalRevenueLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalRevenueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(totalRevenueLabel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Sale");
        saveButton = new JButton("Save Report");
        refreshButton = new JButton("Refresh");
        backButton = new JButton("Back to Stock Management");
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // Load stock data
        loadStockData();

        // Add action listeners
        addButton.addActionListener(e -> addSale());
        saveButton.addActionListener(e -> saveReport());
        refreshButton.addActionListener(e -> refreshSales());
        backButton.addActionListener(e -> returnToStockManagement());
    }

    private void loadStockData() {
        stockData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stockData.add(line.split(","));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading stock data: " + e.getMessage());
        }
    }

    private void addSale() {
        // Prompt user for sale details
        String itemName = JOptionPane.showInputDialog(this, "Enter item name:");
        if (itemName == null || itemName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Item name cannot be empty.");
            return;
        }

        String quantityStr = JOptionPane.showInputDialog(this, "Enter quantity sold:");
        if (quantityStr == null || quantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quantity cannot be empty.");
            return;
        }

        int quantitySold;
        try {
            quantitySold = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Please enter a number.");
            return;
        }

        // Find item in stock data
        String[] item = null;
        for (String[] stockItem : stockData) {
            if (stockItem[0].equalsIgnoreCase(itemName)) {
                item = stockItem;
                break;
            }
        }

        if (item == null) {
            JOptionPane.showMessageDialog(this, "Item not found in stock.");
            return;
        }

        // Check stock availability
        int currentStock = Integer.parseInt(item[1]);
        if (quantitySold > currentStock) {
            JOptionPane.showMessageDialog(this, "Insufficient stock. Current stock: " + currentStock);
            return;
        }

        // Update stock and calculate total price
        double pricePerUnit = Double.parseDouble(item[2]);
        double totalPrice = quantitySold * pricePerUnit;

        // Update stock in the list
        item[1] = String.valueOf(currentStock - quantitySold);

        // Add sale to the table
        DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
        model.addRow(new Object[]{itemName, quantitySold, pricePerUnit, totalPrice});

        // Update total revenue
        totalRevenue += totalPrice;
        totalRevenueLabel.setText("Total Revenue: $" + String.format("%.2f", totalRevenue));
    }

    private void saveReport() {
        // Save the sales data to a daily report file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE, true))) {
            DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String itemName = model.getValueAt(i, 0).toString();
                String quantitySold = model.getValueAt(i, 1).toString();
                String pricePerUnit = model.getValueAt(i, 2).toString();
                String totalPrice = model.getValueAt(i, 3).toString();
                writer.write(itemName + "," + quantitySold + "," + pricePerUnit + "," + totalPrice);
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving daily report: " + e.getMessage());
            return;
        }

        // Save updated stock data back to the inventory file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] stockItem : stockData) {
                writer.write(String.join(",", stockItem));
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating stock data: " + e.getMessage());
            return;
        }

        JOptionPane.showMessageDialog(this, "Daily report saved successfully!");
    }

    private void refreshSales() {
        // Clear the table
        DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
        model.setRowCount(0);

        // Reset total revenue
        totalRevenue = 0;
        totalRevenueLabel.setText("Total Revenue: $0.00");

        // Clear the report file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REPORT_FILE))) {
            writer.write(""); // Empty the file
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error refreshing report file: " + e.getMessage());
        }

        JOptionPane.showMessageDialog(this, "Screen and report refreshed for a new day!");
    }

    private void returnToStockManagement() {
        dispose();
        new StockManagementGUI().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DailyReportGUI().setVisible(true));
    }
}

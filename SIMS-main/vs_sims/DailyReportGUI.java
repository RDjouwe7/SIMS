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
    private boolean isReportSaved = false; // Tracks whether the report is saved
    private static final String FILE_PATH = "inventorydata.txt";
    private static final String REPORT_FILE = "dailyreport.txt";
    private List<String[]> stockData;

    public DailyReportGUI() {
       // Set frame properties
                    // Setting the Icon, Title and sizes
        ImageIcon icon = new ImageIcon("mainlogo.png");
        setIconImage(icon.getImage());
        setTitle("Daily Sales Report");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Handle close manually
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

        // Load persisted sales data
        loadSalesData();

        // Add action listeners
        addButton.addActionListener(e -> addSale());
        saveButton.addActionListener(e -> {
            saveReport();
            isReportSaved = true; // Mark report as saved
        });
        refreshButton.addActionListener(e -> {
            if (confirmSaveBeforeAction("refresh the sales")) {
                refreshSales();
            }
        });
        backButton.addActionListener(e -> {
            if (confirmSaveBeforeAction("return to Stock Management")) {
                returnToStockManagement();
            }
        });

        // Add a window listener to handle close action
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                if (confirmSaveBeforeAction("exit")) {
                    dispose();
                    new MainMenuGUI().setVisible(true);
                }
            }
        });
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

    private void loadSalesData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(REPORT_FILE))) {
            DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                model.addRow(data);
                totalRevenue += Double.parseDouble(data[3]); // Update total revenue
            }
            totalRevenueLabel.setText("Total Revenue: $" + String.format("%.2f", totalRevenue));
        } catch (FileNotFoundException e) {
            // No report file yet, start fresh
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading sales data: " + e.getMessage());
        }
    }

    private void addSale() {
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

        int currentStock = Integer.parseInt(item[1]);
        if (quantitySold > currentStock) {
            JOptionPane.showMessageDialog(this, "Insufficient stock. Current stock: " + currentStock);
            return;
        }

        double pricePerUnit = Double.parseDouble(item[2]);
        double totalPrice = quantitySold * pricePerUnit;

        // Update stock
        item[1] = String.valueOf(currentStock - quantitySold);

        DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
        model.addRow(new Object[]{itemName, quantitySold, pricePerUnit, totalPrice});

        totalRevenue += totalPrice;
        totalRevenueLabel.setText("Total Revenue: $" + String.format("%.2f", totalRevenue));
        isReportSaved = false; // Mark report as unsaved
    }

    private void saveReport() {
        try (BufferedWriter reportWriter = new BufferedWriter(new FileWriter(REPORT_FILE))) {
            DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    reportWriter.write(model.getValueAt(i, j).toString());
                    if (j < model.getColumnCount() - 1) {
                        reportWriter.write(",");
                    }
                }
                reportWriter.newLine();
            }

            try (BufferedWriter stockWriter = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String[] stockItem : stockData) {
                    stockWriter.write(String.join(",", stockItem));
                    stockWriter.newLine();
                }
            }

            JOptionPane.showMessageDialog(this, "Report and stock data saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving report: " + e.getMessage());
        }
    }

    private void refreshSales() {
        DefaultTableModel model = (DefaultTableModel) salesTable.getModel();
        model.setRowCount(0); // Clear the table
        totalRevenue = 0;
        totalRevenueLabel.setText("Total Revenue: $0.00");
        new File(REPORT_FILE).delete(); // Delete the report file
        JOptionPane.showMessageDialog(this, "Sales refreshed!");
    }

    private boolean confirmSaveBeforeAction(String action) {
        if (!isReportSaved) {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "You have unsaved changes. Would you like to save the report before you " + action + "?",
                "Unsaved Changes",
                JOptionPane.YES_NO_CANCEL_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                saveReport();
                isReportSaved = true;
                return true; // Proceed after saving
            } else if (choice == JOptionPane.NO_OPTION) {
                return true; // Proceed without saving
            } else {
                return false; // Cancel the action
            }
        }
        return true; // Proceed if no unsaved changes
    }

    private void returnToStockManagement() {
        dispose();
        new StockManagementGUI().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DailyReportGUI().setVisible(true));
    }
}

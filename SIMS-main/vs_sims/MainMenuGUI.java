import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenuGUI extends JFrame {

    private boolean isDarkMode = false;  // Track whether dark mode is enabled
    private JPanel panel;

    // Example ArrayList of menu items
    private ArrayList<String> menuItems;

    public MainMenuGUI() {

        setResizable(false);
        ImageIcon icon = new ImageIcon("sims.png"); // Logo icon
        setIconImage(icon.getImage());
        setTitle("SIMS - Main Menu");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize the ArrayList of menu items
        menuItems = new ArrayList<>();
        menuItems.add("Inventory Management");
        menuItems.add("Stock Management");
        //menuItems.add("Demand Forecasting");
        menuItems.add("Expiration/Stock Info");
       // menuItems.add("Supplier Management");
        menuItems.add("Reporting & Analysis");
        menuItems.add("User Settings");
        menuItems.add("Help & Support");
        menuItems.add("Exit");

        // Create panel for buttons
        panel = new JPanel();
        panel.setLayout(new GridLayout(menuItems.size(), 2, 10, 10)); // Dynamically adjust grid size based on menu items

        // Add buttons dynamically from the menuItems ArrayList
        for (String item : menuItems) {
            addMenuButton(panel, item);
        }

        // Add the panel to the frame
        add(panel);
        updateUI(); // Apply the initial UI settings
        setVisible(true);
    }

    // Helper method to add buttons to the panel
    private void addMenuButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));

        // Define the action for each button based on its text
        button.addActionListener(e -> {
            switch (text) {
                case "Inventory Management":
                    openInventoryManagement();
                    break;
                case "Stock Management":
                    openStockManagement();
                    break;
                case "Demand Forecasting":
                    JOptionPane.showMessageDialog(null, "Demand Forecasting selected.");
                    break;
                case "Expiration/Stock Info":
                    CheckExpiration.checkExpiration();
                    break;
                case "Supplier Management":
                    JOptionPane.showMessageDialog(null, "Supplier Management selected.");
                    break;
                case "Reporting & Analysis":
                     dispose();
                     SwingUtilities.invokeLater(() -> {
                    new DailyReportGUI().setVisible(true); }); 
                    break;
                case "User Settings":
                    new SettingsGUI(this).setVisible(true);
                    break;
                case "Help & Support":
                    new HelpSupportGUI().setVisible(true);
                    break;
                case "Exit":
                    System.exit(0);
                    break;
            }
        });

        panel.add(button);
    }

    // Method to open Inventory Management GUI
    private void openInventoryManagement() {
        dispose(); // Close the current Main Menu window
        SwingUtilities.invokeLater(() -> {
            new InventoryManagementGUI().setVisible(true); });// Open the Inventory Management window
    }

    // Method to open Stock Management GUI
    private void openStockManagement() {
        dispose(); // Close the current Main Menu window
        SwingUtilities.invokeLater(() -> {
            new StockManagementGUI().setVisible(true); }); // Open the Stock Management window
    }

    // Method to toggle Dark Mode
    public void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        updateUI();
    }

    // Update the UI colors based on the mode
    private void updateUI() {
        if (isDarkMode) {
            // Dark Mode settings
            getContentPane().setBackground(Color.DARK_GRAY);
            panel.setBackground(Color.DARK_GRAY);
            setTitle("SIMS - Main Menu (Dark Mode)");
            setForeground(Color.WHITE);
        } else {
            // Light Mode settings
            getContentPane().setBackground(Color.WHITE);
            panel.setBackground(Color.WHITE);
            setTitle("SIMS - Main Menu");
            setForeground(Color.BLACK);
        }

        // Update button colors and text
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (isDarkMode) {
                    button.setBackground(Color.GRAY);
                    button.setForeground(Color.WHITE);
                } else {
                    button.setBackground(Color.LIGHT_GRAY);
                    button.setForeground(Color.BLACK);
                }
            }
        }
    }

    // Main method to launch the MainMenuGUI window
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI());
    }
}

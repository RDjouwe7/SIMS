import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenuGUI extends JFrame {

    private boolean isDarkMode = false;  // Track whether dark mode is enabled for the settings
    private JPanel panel;

    private ArrayList<String> menuItems;

    public MainMenuGUI() {

         // Set frame properties

                  // Setting the Icon, Title and sizes
        ImageIcon icon = new ImageIcon("mainlogo.png"); // Logo icon
        setResizable(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setIconImage(icon.getImage());

        setTitle("SIMS - Main Menu");
        setSize(1500, 800);
        setLocationRelativeTo(null);

        // Added a Window Listener for exit confirmation
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                showExitConfirmation();
            }
        });

        // Initialize the ArrayList of menu items
        menuItems = new ArrayList<>();
        menuItems.add("Inventory Management");
        menuItems.add("Stock Management");
        menuItems.add("Expiration/Stock Info");
        menuItems.add("Reporting & Analysis");
        menuItems.add("User Settings");
        menuItems.add("Help & Support");
        menuItems.add("Sign Out");

        // Create panel for buttons
        panel = new JPanel();
        panel.setLayout(new GridLayout(menuItems.size(), 2, 10, 10)); // Dynamically adjust grid size based on menu items

        // Add buttons dynamically from the menuItems ArrayList
        for (String item : menuItems) {
            addMenuButton(panel, item);
        }

        // Add the panel to the frame
        add(panel);
        updateUI(); // This is for UI settings
        setVisible(true);
    }

    // we need a method to add buttons to the panel
    private void addMenuButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));

        // we need if statements or a switch case here for each button
        button.addActionListener(e -> {
            switch (text) {
                case "Inventory Management":
                    dispose(); // Close the current Main Menu window
                    SwingUtilities.invokeLater(() -> new InventoryManagementGUI().setVisible(true));
                    break;
                case "Stock Management":
                    dispose(); // Close the current Main Menu window
                    SwingUtilities.invokeLater(() -> new StockManagementGUI().setVisible(true));
                    break;
                case "Expiration/Stock Info":
                    CheckExpiration.checkExpiration();
                    break;
                case "Reporting & Analysis":
                    dispose();
                    SwingUtilities.invokeLater(() -> new DailyReportGUI().setVisible(true));
                    break;
                case "User Settings":
                    new SettingsGUI(this).setVisible(true);
                    break;
                case "Help & Support":
                    new HelpSupportGUI().setVisible(true);
                    break;
                case "Sign Out":
                    showreturnConfirmation();
                    break;
            }
        });

        panel.add(button);
    }

    // Method to confirm exit
    private void showExitConfirmation() {
        int choice = JOptionPane.showConfirmDialog(this,"Are you sure you want to exit?","Confirm Exit",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    //Method to confirm return to Login
    private void showreturnConfirmation() {
        int choice = JOptionPane.showConfirmDialog(this,"Are you sure you want to sign out? Unsaved changes will be lost.","Confirm Sign Out",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> new Login().setVisible(true));
        }
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

    // Main method to launch the MainMenuGUI window by itself
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI());
    }
}


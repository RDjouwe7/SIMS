import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;

public class MainMenuGUI extends JFrame {

    private boolean isDarkMode = false;  // Track whether dark mode is enabled
    private JPanel panel;

    public MainMenuGUI() {
        setResizable(false);
        ImageIcon icon = new ImageIcon("sims.png"); // Logo icon
        setIconImage(icon.getImage());
        setTitle("SIMS - Main Menu");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel for buttons
        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        
        // Initialize and add buttons to the panel
        addMenuButton(panel, "Inventory Management");
        addMenuButton(panel, "Stock Management");
        addMenuButton(panel, "Demand Forecasting");
        addMenuButton(panel, "Expiration Tracking");
        addMenuButton(panel, "Supplier Management");
        addMenuButton(panel, "Reporting & Analysis");
        
        // Settings button to open settings window
        JButton settingsButton = new JButton("User Settings");
        settingsButton.addActionListener(e -> new SettingsGUI(this).setVisible(true));
        panel.add(settingsButton);

        // Help button
        addMenuButton(panel, "Help & Support");

        // Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        panel.add(exitButton);

        add(panel);
        updateUI(); // Apply the initial UI settings
        setVisible(true);
    }

    private void addMenuButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.addActionListener(e -> JOptionPane.showMessageDialog(null, text + " selected."));
        panel.add(button);
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

    // Method to toggle language and update button text
    public void toggleLanguage(boolean isFrench) {
        if (isFrench) {
            setTitle("SIMS - Menu Principal");
            ((JButton)getContentPane().getComponent(0)).setText("Gestion des stocks");
            ((JButton)getContentPane().getComponent(1)).setText("Gestion des inventaires");
            ((JButton)getContentPane().getComponent(2)).setText("Prévision de la demande");
            ((JButton)getContentPane().getComponent(3)).setText("Suivi de l'expiration");
            ((JButton)getContentPane().getComponent(4)).setText("Gestion des fournisseurs");
            ((JButton)getContentPane().getComponent(5)).setText("Rapports & Analyses");
            ((JButton)getContentPane().getComponent(6)).setText("Paramètres utilisateur");
            ((JButton)getContentPane().getComponent(7)).setText("Aide & Support");
            ((JButton)getContentPane().getComponent(8)).setText("Quitter");
        } else {
            setTitle("SIMS - Main Menu");
            ((JButton)getContentPane().getComponent(0)).setText("Inventory Management");
            ((JButton)getContentPane().getComponent(1)).setText("Stock Management");
            ((JButton)getContentPane().getComponent(2)).setText("Demand Forecasting");
            ((JButton)getContentPane().getComponent(3)).setText("Expiration Tracking");
            ((JButton)getContentPane().getComponent(4)).setText("Supplier Management");
            ((JButton)getContentPane().getComponent(5)).setText("Reporting & Analysis");
            ((JButton)getContentPane().getComponent(6)).setText("User Settings");
            ((JButton)getContentPane().getComponent(7)).setText("Help & Support");
            ((JButton)getContentPane().getComponent(8)).setText("Exit");
        }
    }



    // Main method to launch the MainMenuGUI window
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenuGUI());
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingsGUI extends JFrame {
    public SettingsGUI(MainMenuGUI mainMenu) {

       
        setTitle("User Settings");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        // Dropdown for language selection
        JLabel languageLabel = new JLabel("Switch mode:", SwingConstants.CENTER);
        add(languageLabel);

        String[] languages = {"English", "French"};
        JComboBox<String> languageDropdown = new JComboBox<>(languages);
        //add(languageDropdown);
        setResizable(false);
        ImageIcon icon = new ImageIcon("sims.png"); // Logo icon
        setIconImage(icon.getImage());

        // Dark Mode toggle button
        JButton darkModeButton = new JButton("Dark Mode/Light Mode");
        darkModeButton.addActionListener((ActionEvent e) -> {
            mainMenu.toggleDarkMode();
            
            JOptionPane.showMessageDialog(this, "Mode Switch Successfully");
        });
        add(darkModeButton);

        // Apply button to save settings
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener((ActionEvent e) -> {
            String selectedLanguage = (String) languageDropdown.getSelectedItem();
            boolean isFrench = selectedLanguage.equals("French");
            mainMenu.toggleLanguage(isFrench);
            JOptionPane.showMessageDialog(this, "Settings applied successfully!");
            dispose(); // Close the settings window
        });
        //add(applyButton);
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingsGUI extends JFrame {
    public SettingsGUI(MainMenuGUI mainMenu) {

        // Set frame properties

                  // Setting the Icon, Title and sizes

        ImageIcon icon = new ImageIcon("mainlogo.png"); 
        setIconImage(icon.getImage());
        setTitle("User Settings");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));
        setResizable(false);



        JLabel lightningLabel = new JLabel("Switch mode:", SwingConstants.CENTER);
        add(lightningLabel);
        
        // Dark Mode toggle button
        JButton darkModeButton = new JButton("Dark Mode/Light Mode");
        darkModeButton.addActionListener((ActionEvent e) -> {
            mainMenu.toggleDarkMode();
            JOptionPane.showMessageDialog(this, "Mode Switch Successfully");
        });
        
        add(darkModeButton);

    }
}
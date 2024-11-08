import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame {

    public MainMenuGUI() {
        setTitle("SIMS - Main Menu");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        // Create buttons for each main menu option
        addMenuButton(panel, "Inventory Management");
        addMenuButton(panel, "Stock Management");
        addMenuButton(panel, "Demand Forecasting");
        addMenuButton(panel, "Expiration Tracking");
        addMenuButton(panel, "Supplier Management");
        addMenuButton(panel, "Reporting & Analysis");
        addMenuButton(panel, "User Settings");
        addMenuButton(panel, "Help & Support");
        addExitButton(panel, "Exit");

        add(panel);
        setVisible(true);
    }

    private void addMenuButton(JPanel panel, String text) {
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, text + " selected.");
            }
        });
        panel.add(button);
    }

    private void addExitButton(JPanel panel, String text) {
        JButton exitButton = new JButton(text);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(exitButton);
    }
}
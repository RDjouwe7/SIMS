import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password123";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginGUI() {
        setTitle("Inventory System Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create and set up panel for login form
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));

        // Add components to the panel
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel());  // Spacer
        panel.add(loginButton);

        add(panel);

        // Action Listener for the Login Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            this.dispose();  // Close the login window
            new MainMenuGUI();  // Open the main menu
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI login = new LoginGUI();
            login.setVisible(true);
        });
    }
}
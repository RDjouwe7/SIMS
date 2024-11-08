import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

    private static final String USERNAME = "111";
    private static final String PASSWORD = "111";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Image backgroundImage; // To hold the background image

    public LoginGUI() {
        setResizable(false);
        setLayout(new BorderLayout()); // Set layout for frame

        // Load the background image
        //backgroundImage = new ImageIcon("wp9031357.jpg").getImage(); // Replace with your image path

        // Set frame properties
        ImageIcon icon = new ImageIcon("sims.png"); // Replace with your image path
        setIconImage(icon.getImage());
        setTitle("Sims Login");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create welcome and quote labels
        JLabel welcomeLabel = new JLabel("Welcome to SIMS", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel quoteLabel = new JLabel("We’re glad to have you here. Let's get started", SwingConstants.CENTER);
        quoteLabel.setFont(new Font("Arial", Font.ITALIC, 16));

        // Add welcome and quote labels to a panel
        JPanel messagePanel = new JPanel(new GridLayout(2, 1));
        messagePanel.add(welcomeLabel);
        messagePanel.add(quoteLabel);

        // Add the message panel to the top of the frame
        add(messagePanel, BorderLayout.NORTH);

        // Create login panel for the form
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        // Add components to the login form panel
        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel());  // Spacer
        panel.add(loginButton);

        // Add the login form panel to the center of the frame
        add(panel, BorderLayout.CENTER);

        // Action Listener for the Login Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            this.dispose();  // Close the login window
            // Ensure MainMenuGUI is implemented and opens the main menu
            // new MainMenuGUI();  // Uncomment when MainMenuGUI is available
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


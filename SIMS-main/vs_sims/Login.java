import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Login extends JFrame {

    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private int loginAttempts = 0;

    public Login() {
        setResizable(false);
        setLayout(new BorderLayout());

        // Set frame properties
        ImageIcon icon = new ImageIcon("sims.png");
        if (icon.getImage() == null) {
            System.out.println("Logo image not found!");
        } else {
            setIconImage(icon.getImage());
        }
        setTitle("Sims Login");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creating welcome and quote labels
        JLabel welcomeLabel = new JLabel("Welcome to SIMS", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel quoteLabel = new JLabel("We’re glad to have you here. Let's get started", SwingConstants.CENTER);
        quoteLabel.setFont(new Font("Arial", Font.ITALIC, 16));

        JPanel messagePanel = new JPanel(new GridLayout(2, 1));
        messagePanel.add(welcomeLabel);
        messagePanel.add(quoteLabel);
        add(messagePanel, BorderLayout.NORTH);
        messagePanel.setBackground(Color.gray);

        // Create login panel for the form
        JPanel panel = new JPanel();
        panel.setBackground(Color.gray);
        panel.setLayout(new GridLayout(3, 2, 50, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JLabel userLabel = new JLabel("Username:", SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(userLabel);
        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:", SwingConstants.CENTER);
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(passLabel);
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel, BorderLayout.SOUTH);

        // Background image panel with overridden paintComponent
        JPanel ipanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Ensure image exists
                File imgFile = new File("sims_bkg.png");
                if (!imgFile.exists()) {
                    System.out.println("Image not found at: " + imgFile.getAbsolutePath());
                } else {
                    ImageIcon infoIcon = new ImageIcon("sims_bkg.png");
                    Image img = infoIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                    g.drawImage(img, 0, 0, this);  // Draw the image on the panel
                }
            }
        };
        ipanel.setLayout(new BorderLayout());
        add(ipanel, BorderLayout.CENTER);

        // Login action
        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            this.dispose();
            new MainMenuGUI();
        } else {
            loginAttempts++;
            if (loginAttempts >= 5) {
                JOptionPane.showMessageDialog(this, "Maximum login attempts reached. Access denied.", "Error", JOptionPane.ERROR_MESSAGE);
                loginButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Try Again", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}

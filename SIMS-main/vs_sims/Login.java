import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private static final String USERNAME = ""; // variable for the username
    private static final String PASSWORD = ""; // variable for the password

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private int loginAttempts = 0;

    public Login() {
        setResizable(false);
        setLayout(new BorderLayout());

        // Set frame properties

                     // Setting the Icon, Title and sizes
        ImageIcon icon = new ImageIcon("mainlogo.png");
        setIconImage(icon.getImage());

        setTitle("Sims Login");
        setSize(700, 740);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

      // We are going to create Three(3) Panels in this JFrame

        // Creating  Message Panel with its labels (NORTH)

        JPanel messagePanel = new JPanel(new GridLayout(2, 1));

        JLabel welcomeLabel = new JLabel("Welcome to SIMS", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel messageLabel = new JLabel("We’re glad to have you here. Let's get started", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 16));


        messagePanel.add(welcomeLabel);
        messagePanel.add(messageLabel);
        messagePanel.setBackground(Color.gray);

        add(messagePanel, BorderLayout.NORTH);

        // Creating the 2nd Panel for the background Image withs its label (CENTER)

        ImageIcon backimage = new ImageIcon("logo.jpg");

        JPanel backgroundPanel = new JPanel();
    
        JLabel backgroundLabel = new JLabel(backimage);

        backgroundPanel.add(backgroundLabel);

        add(backgroundPanel, BorderLayout.CENTER);

        // Creating the 3rd Panel for the Login form withs its labels (SOUTH)
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


        // Login action calls the handleLogin method
        loginButton.addActionListener(e -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Check if credentials are right!
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            
            ImageIcon successIcon = new ImageIcon("check.png");

        JOptionPane.showMessageDialog( null,  "    Login successful!", "Access Granted", JOptionPane.INFORMATION_MESSAGE, successIcon);
            

            //Removes the login Page and calls the Main Menu
            this.dispose(); 
            new MainMenuGUI();

        } else {
            loginAttempts++;
            if (loginAttempts >= 2) {
                JOptionPane.showMessageDialog(this, "Maximum login attempts reached. Access denied.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                
                loginButton.setEnabled(false);
                JOptionPane.showMessageDialog(null, "Please see a manager for assistance. Contact Support if problem persists.", "System Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.WARNING_MESSAGE);
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

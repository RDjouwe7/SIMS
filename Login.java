import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    //Klea please review these !!!

    private JTextField usernameField; // for the usernname
    private JPasswordField passwordField; // for the password
    private JButton loginButton;
    private int loginAttempts = 0;
    

    public Login() {
        setResizable(false);
        setLayout(new BorderLayout()); // Set layout for frame

      

        // Set frame properties
        ImageIcon icon = new ImageIcon("sims.png"); // our logo (the icon)
        setIconImage(icon.getImage());
        setTitle("Sims Login"); // our title
        setSize(600, 600); // the size 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ends the program when x is clicked
        setLocationRelativeTo(null); // centers the frame

        // Creating welcome and quote labels
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
        messagePanel.setBackground(Color.gray);

        // Create login panel for the form
        JPanel panel = new JPanel();
        panel.setBackground(Color.gray);
        panel.setLayout(new GridLayout(3, 2, 50, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        // Add components to the login form panel
        JLabel userLabel = new JLabel("Username:", SwingConstants.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(userLabel);
        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passLabel = new JLabel("Password:",SwingConstants.CENTER);
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(passLabel);
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        panel.add(new JLabel());  // Spacer
        panel.add(loginButton);

        // Add the login form panel to the center of the frame
        add(panel, BorderLayout.SOUTH);

        JPanel ipanel = new JPanel();
        ipanel.setBackground(Color.lightGray);
        ImageIcon infoIcon = new ImageIcon("sims_bkg.png");  // Replace with your image path
        JLabel imageLabel = new JLabel(infoIcon);
        ipanel.add(imageLabel, BorderLayout.WEST);
        add(ipanel);

        // If the login button is pressed 
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
            new MainMenuGUI();  // Please Klea Uncomment when MainMenuGUI is available
        } else {
            loginAttempts++;  // Increment the login attempt counter
            if (loginAttempts >= 5) {
                JOptionPane.showMessageDialog(this, "Maximum login attempts reached. Access denied. Please contact support at: (508) 714-9732", "error", JOptionPane.ERROR_MESSAGE);
                loginButton.setEnabled(false); // Disable the login button
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.","Try Again",JOptionPane.WARNING_MESSAGE);
            }
        }
        
    }

    public static void main(String[] args) {
        //  By queuing tasks in invokeLater, we ensure the GUI creation and updates are handled in a thread-safe way on the EDT
        // Read about Lambda Expressions in w3 schools 
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}


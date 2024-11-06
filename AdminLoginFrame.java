import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AdminLoginFrame() {
        setTitle("Admin Login");
        setSize(1200, 700); // Set frame size to match the background image
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Use absolute layout for precise positioning

        // Load the background image and set it as a JLabel
        ImageIcon backgroundIcon = new ImageIcon("images.jfif"); // Replace with your image path
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1200, 700); // Set bounds to cover the entire frame
     
        // Create a panel for login fields
      /*  JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBounds(450, 200, 300, 100); // Set specific bounds for positioning on the frame
        inputPanel.setOpaque(false); // Make the input panel transparent

        // Username and password fields
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        // Create and position the login button
        loginButton = new JButton("Login");
        loginButton.setBounds(550, 320, 100, 30); // Position below the input panel
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Simple authentication example
                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    // Proceed to next screen or functionality
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add components to the frame
      //  add(inputPanel);
       // add(loginButton);
        add(backgroundLabel); // Add the background image last so it is at the back

        // Adjust layering to ensure background is behind other components
        getLayeredPane().add(backgroundLabel, Integer.valueOf(Integer.MIN_VALUE));
    }*/
    }

    /* // Sample authentication method
    private boolean authenticate(String username, String password) {
        // Replace with actual validation logic
        return username.equals("admin") && password.equals("password123");
    }*/

    public static void main(String[] args) {
        
            AdminLoginFrame loginFrame = new AdminLoginFrame();
            loginFrame.setVisible(true);
       
    }
}
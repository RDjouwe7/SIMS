import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpSupportGUI extends JFrame {

    public HelpSupportGUI() {
        setTitle("Help & Support");
        ImageIcon icon = new ImageIcon("sims.png"); // Logo icon
        setIconImage(icon.getImage());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel for the Help & Support options
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        
        // Button for FAQs
        JButton faqButton = new JButton("Frequently Asked Questions");
        faqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayFAQ();
            }
        });
        
        // Button for Troubleshooting
        JButton troubleshootingButton = new JButton("Troubleshooting Guide");
        troubleshootingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayTroubleshooting();
            }
        });
        
        // Button for Contact Support
        JButton contactSupportButton = new JButton("Contact Support");
        contactSupportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayContactInfo();
            }
        });
        
        // Add buttons to main panel
        mainPanel.add(faqButton);
        mainPanel.add(troubleshootingButton);
        mainPanel.add(contactSupportButton);

        add(mainPanel);
    }

    // Display FAQ Information
    private void displayFAQ() {
        String faqText = "<html><h2>Frequently Asked Questions</h2>" +
                "<p><b>Q1: How do I change the password?</b><br>A: Contact Support</p>" +
                "<p><b>Q2: How can I close the Main Menu page?</b><br>A: You can only close the Main page using the Exit button</p></html>";
        
        JOptionPane.showMessageDialog(this, faqText, "FAQs", JOptionPane.INFORMATION_MESSAGE);
    }

    // Display Troubleshooting Information
    private void displayTroubleshooting() {
        String troubleshootingText = "<html><h2>Troubleshooting Guide</h2>" +
                "<p><b>Issue:</b> Unable to print the pdf <br>Check your internet connection or restart the app</p>" +
                "<p><b>Issue:</b> Application is running slowly.<br>Try closing other programs or restarting the app.</p>" +  "<br>" +
                "<p>For more help, contact support.</p></html>";
        
        JOptionPane.showMessageDialog(this, troubleshootingText, "Troubleshooting Guide", JOptionPane.INFORMATION_MESSAGE);
    }

    // Display Contact Support Information
    private void displayContactInfo() {
        String contactInfo = "<html><h2>Contact Support</h2>" +
                "<p>Email: rwoubinwoudjouwe@worcester.edu / kmustafaj@worcester.edu</p>" +
                "<p>Phone: +1 (805) 612-7924 / +1 (508) 714-9732 </p>" +
                "<p>Hours: Mon-Fri, 1PM - 2PM</p>" +
                "<p>We're here to help!</p>" + "<br>" +
                "<p>Thanks for using our system!</p></html>";
        
        JOptionPane.showMessageDialog(this, contactInfo, "Contact Support", JOptionPane.INFORMATION_MESSAGE);
    }
}
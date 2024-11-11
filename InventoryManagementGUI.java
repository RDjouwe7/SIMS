import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class InventoryManagementGUI extends JFrame {
    private ArrayList<Item> items;
    private DefaultListModel<String> listModel;

    public InventoryManagementGUI(ArrayList<Item> sharedItems) {
        this.items = sharedItems;

        setTitle("Inventory Management");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        // Item Name
        JLabel nameLabel = new JLabel("Item Name:");
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        panel.add(nameField);

        // Category
        JLabel categoryLabel = new JLabel("Category:");
        panel.add(categoryLabel);

        JTextField categoryField = new JTextField();
        panel.add(categoryField);

        // Price
        JLabel priceLabel = new JLabel("Price:");
        panel.add(priceLabel);

        JTextField priceField = new JTextField();
        panel.add(priceField);

        // Amount
        JLabel amountLabel = new JLabel("Amount:");
        panel.add(amountLabel);

        JTextField amountField = new JTextField();
        panel.add(amountField);

        // Expiration Date
        JLabel expirationDateLabel = new JLabel("Expiration Date (MM/DD/YYYY):");
        panel.add(expirationDateLabel);

        JTextField expirationDateField = new JTextField();
        panel.add(expirationDateField);

        // Add Item Button
        JButton addButton = new JButton("Add Item");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String category = categoryField.getText();
                double price = Double.parseDouble(priceField.getText());
                int amount = Integer.parseInt(amountField.getText());
                @SuppressWarnings("deprecation")
                Date expirationDate = new Date(expirationDateField.getText());  // Simplified for example

                Item newItem = new Item(name, category, price, amount, expirationDate);
                items.add(newItem);
                updateItemList();
                saveData();
            }
        });
        panel.add(addButton);

        // Inventory List
        listModel = new DefaultListModel<>();
        JList<String> itemList = new JList<>(listModel);
        panel.add(new JScrollPane(itemList));

        // Back to Main Menu button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainMenuGUI();
            }
        });
        panel.add(backButton);

        add(panel);
        setVisible(true);

        // Load and display existing items
        updateItemList();
    }

    private void updateItemList() {
        listModel.clear();
        for (Item item : items) {
            listModel.addElement(item.getName());
        }
    }

    private void saveData() {
        // Save items to a file
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManagementGUI(new ArrayList<>()));
    }
}

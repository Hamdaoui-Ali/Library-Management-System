package Vue;

import Controller.UserController;
import Model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView {
    private JFrame frame;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("User Management");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Table for displaying users
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Role"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel for user actions
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(5, 2));

        // Input fields
        JTextField idField = new JTextField(); // ID field
        idField.setEditable(false); // Make it non-editable
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Member", "Librarian", "Admin"});

        actionPanel.add(new JLabel("ID:"));
        actionPanel.add(idField);
        actionPanel.add(new JLabel("Name:"));
        actionPanel.add(nameField);
        actionPanel.add(new JLabel("Email:"));
        actionPanel.add(emailField);
        actionPanel.add(new JLabel("Password:"));
        actionPanel.add(passwordField);
        actionPanel.add(new JLabel("Role:"));
        actionPanel.add(roleBox);

        frame.add(actionPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add User");
        JButton deleteButton = new JButton("Delete User");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Load users into table
        loadUsersIntoTable();

        // Set the initial value of the ID field
        idField.setText(String.valueOf(userController.getNextUserId()));

        // Add user action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String password = new String(passwordField.getPassword());
                    String role = (String) roleBox.getSelectedItem();

                    // Add user with automatically generated ID
                    userController.addUserWithGeneratedId(name, email, password, role);
                    loadUsersIntoTable();
                    JOptionPane.showMessageDialog(frame, "User added successfully!");

                    // Update the ID field with the next ID
                    idField.setText(String.valueOf(userController.getNextUserId()));

                    // Clear input fields
                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error adding user: " + ex.getMessage());
                }
            }
        });

        // Delete user action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                        userController.deleteUser(id);
                        loadUsersIntoTable();
                        JOptionPane.showMessageDialog(frame, "User deleted successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error deleting user: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a user to delete.");
                }
            }
        });

        frame.setVisible(true);
    }

    private void loadUsersIntoTable() {
        tableModel.setRowCount(0); // Clear table
        for (User user : userController.getAllUsers()) {
            tableModel.addRow(new Object[]{user.getId(), user.getName(), user.getEmail(), user.getRole()});
        }
    }

    public static void main(String[] args) {
        // Example usage
        UserController userController = new UserController();
        new UserView(userController);
    }
}

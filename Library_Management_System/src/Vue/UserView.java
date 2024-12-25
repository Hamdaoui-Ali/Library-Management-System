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
        frame = new JFrame("Gestion des Utilisateurs");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(230, 240, 255));

        // Top Panel for Inputs
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Informations Utilisateur"));
        inputPanel.setBackground(new Color(200, 220, 255));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleBox = new JComboBox<>(new String[]{"Member", "Librarian", "Admin"});

        inputPanel.add(new JLabel("ID:", JLabel.RIGHT));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Nom:", JLabel.RIGHT));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Email:", JLabel.RIGHT));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Mot de Passe:", JLabel.RIGHT));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("Rôle:", JLabel.RIGHT));
        inputPanel.add(roleBox);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Center Panel for Table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Email", "Rôle"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Liste des Utilisateurs"));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel for Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE, 2), "Actions"));
        buttonPanel.setBackground(new Color(200, 220, 255));

        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(144, 238, 144));
        JButton modifyButton = new JButton("Modifier");
        modifyButton.setBackground(new Color(255, 223, 186));
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(255, 160, 122));

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Load users into table
        loadUsersIntoTable();

        // Add User Action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();
                    String role = (String) roleBox.getSelectedItem();

                    if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && role != null) {
                        userController.addUserWithGeneratedId(name, email, password, role);
                        loadUsersIntoTable();
                        JOptionPane.showMessageDialog(frame, "Utilisateur ajouté avec succès !");
                        clearFields(idField, nameField, emailField, passwordField);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Veuillez remplir tous les champs.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de l'ajout : " + ex.getMessage());
                }
            }
        });

        // Modify User Action
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText().trim());
                    String name = nameField.getText().trim();
                    String email = emailField.getText().trim();
                    String password = new String(passwordField.getPassword()).trim();
                    String role = (String) roleBox.getSelectedItem();

                    User userToEdit = userController.getAllUsers().stream()
                            .filter(user -> user.getId() == id)
                            .findFirst()
                            .orElse(null);

                    if (userToEdit != null) {
                        if (!name.isEmpty()) userToEdit.setName(name);
                        if (!email.isEmpty()) userToEdit.setEmail(email);
                        if (!password.isEmpty()) userToEdit.setPassword(password);
                        if (role != null) userToEdit.setRole(role);

                        userController.saveUsersToFile();
                        loadUsersIntoTable();
                        JOptionPane.showMessageDialog(frame, "Utilisateur modifié avec succès !");
                        clearFields(idField, nameField, emailField, passwordField);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Utilisateur avec ID " + id + " non trouvé.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID invalide.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Erreur lors de la modification : " + ex.getMessage());
                }
            }
        });

        // Delete User Action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                        userController.deleteUser(id);
                        loadUsersIntoTable();
                        JOptionPane.showMessageDialog(frame, "Utilisateur supprimé avec succès !");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Erreur lors de la suppression : " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un utilisateur à supprimer.");
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

    private void clearFields(JTextField idField, JTextField nameField, JTextField emailField, JPasswordField passwordField) {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        UserController userController = new UserController();
        new UserView(userController);
    }
}

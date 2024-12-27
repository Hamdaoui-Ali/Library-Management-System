package Vue;

import controller.UserController;
import model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserView {
    private JPanel panel;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private UserController userController;
    private JTextField nomField, prenomField, cinField, ageField, emailField, telephoneField, adresseField;
    private List<User> allUsers;

    public UserView(UserController userController) {
        this.userController = userController;
        this.allUsers = userController.getAllUsers();
        initialize();
    }

    private void initialize() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));

        // Formulaire d'entrée
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Informations Utilisateur"));
        inputPanel.setBackground(new Color(230, 240, 255));

        nomField = new JTextField();
        prenomField = new JTextField();
        cinField = new JTextField();
        ageField = new JTextField();
        emailField = new JTextField();
        telephoneField = new JTextField();
        adresseField = new JTextField();

        inputPanel.add(new JLabel("Nom :"));
        inputPanel.add(nomField);
        inputPanel.add(new JLabel("Prénom :"));
        inputPanel.add(prenomField);
        inputPanel.add(new JLabel("CIN :"));
        inputPanel.add(cinField);
        inputPanel.add(new JLabel("Âge :"));
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Email :"));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("Téléphone :"));
        inputPanel.add(telephoneField);
        inputPanel.add(new JLabel("Adresse :"));
        inputPanel.add(adresseField);

        panel.add(inputPanel, BorderLayout.WEST);

        // Table des utilisateurs
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Prénom", "CIN", "Âge", "Email", "Téléphone", "Adresse"}, 0);
        userTable = new JTable(tableModel);
        userTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des Utilisateurs"));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton addButton = new JButton("Ajouter");
        JButton modifyButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Actions des boutons
        addButton.addActionListener(e -> addUser());
        modifyButton.addActionListener(e -> modifyUser());
        deleteButton.addActionListener(e -> deleteUser());

        // Remplir les champs à partir de la ligne sélectionnée
        userTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow >= 0) {
                    populateFieldsFromRow(selectedRow);
                }
            }
        });

        loadUsersIntoTable();
    }

    private void addUser() {
        try {
            String nom = nomField.getText().trim();
            String prenom = prenomField.getText().trim();
            String cin = cinField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String email = emailField.getText().trim();
            String telephone = telephoneField.getText().trim();
            String adresse = adresseField.getText().trim();

            if (nom.isEmpty() || prenom.isEmpty() || cin.isEmpty() || email.isEmpty() || telephone.isEmpty() || adresse.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Veuillez remplir tous les champs.");
                return;
            }

            userController.addUserWithGeneratedId(nom, prenom, cin, age, email, telephone, adresse);
            loadUsersIntoTable();
            clearFields();
            JOptionPane.showMessageDialog(panel, "Utilisateur ajouté avec succès !");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Âge invalide.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "Erreur : " + ex.getMessage());
        }
    }

    private void modifyUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                String nom = nomField.getText().trim();
                String prenom = prenomField.getText().trim();
                String cin = cinField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                String email = emailField.getText().trim();
                String telephone = telephoneField.getText().trim();
                String adresse = adresseField.getText().trim();

                if (nom.isEmpty() || prenom.isEmpty() || cin.isEmpty() || email.isEmpty() || telephone.isEmpty() || adresse.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Veuillez remplir tous les champs.");
                    return;
                }

                userController.updateUser(id, nom, prenom, cin, age, email, telephone, adresse);
                loadUsersIntoTable();
                clearFields();
                JOptionPane.showMessageDialog(panel, "Utilisateur modifié avec succès !");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Âge ou ID invalide.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Erreur : " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Veuillez sélectionner un utilisateur à modifier.");
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                userController.deleteUser(id);
                loadUsersIntoTable();
                clearFields();
                JOptionPane.showMessageDialog(panel, "Utilisateur supprimé avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Erreur : " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Veuillez sélectionner un utilisateur à supprimer.");
        }
    }

    private void loadUsersIntoTable() {
        allUsers = userController.getAllUsers();
        tableModel.setRowCount(0);

        for (User user : allUsers) {
            tableModel.addRow(new Object[]{
                    user.getId(), user.getNom(), user.getPrenom(), user.getCin(),
                    user.getAge(), user.getMail(), user.getTelephone(), user.getAdresse()
            });
        }
    }

    private void populateFieldsFromRow(int rowIndex) {
        nomField.setText(tableModel.getValueAt(rowIndex, 1).toString());
        prenomField.setText(tableModel.getValueAt(rowIndex, 2).toString());
        cinField.setText(tableModel.getValueAt(rowIndex, 3).toString());
        ageField.setText(tableModel.getValueAt(rowIndex, 4).toString());
        emailField.setText(tableModel.getValueAt(rowIndex, 5).toString());
        telephoneField.setText(tableModel.getValueAt(rowIndex, 6).toString());
        adresseField.setText(tableModel.getValueAt(rowIndex, 7).toString());
    }

    private void clearFields() {
        nomField.setText("");
        prenomField.setText("");
        cinField.setText("");
        ageField.setText("");
        emailField.setText("");
        telephoneField.setText("");
        adresseField.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}

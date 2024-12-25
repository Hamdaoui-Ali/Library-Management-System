package Vue;

import Controller.UserController;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuView {
    private JFrame frame;
    private UserController userController;

    public MenuView(User user, UserController userController) {
        this.userController = userController;
        initialize(user);
    }

    private void initialize(User user) {
        frame = new JFrame("Menu Principal - " + user.getRole());
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 1)); // Dynamique selon les options

        // Obtenir les options en fonction du rôle
        List<String> options = user.getAvailableOptions();

        // Ajouter les boutons dynamiquement
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> handleOptionClick(option, user));
            frame.add(button);
        }

        // Ajouter un bouton de déconnexion
        JButton logoutButton = new JButton("Se déconnecter");
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new LoginView();
        });
        frame.add(logoutButton);

        frame.setVisible(true);
    }

    private void handleOptionClick(String option, User user) {
        if (option.equals("Gérer les utilisateurs")) {
            // Vérifiez que le contrôleur existe
            if (userController != null) {
                new UserView(userController); // Ouvre l'interface de gestion des utilisateurs
            } else {
                JOptionPane.showMessageDialog(frame, "Erreur : le contrôleur des utilisateurs n'est pas initialisé.");
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Vous avez cliqué sur : " + option);
        }
    }
}

package Vue;

import controller.UserController;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserController userController = new UserController(); // Initialisation du contrôleur
            new MenuView(userController); // Passer le contrôleur au menu principal
        });
    }
}

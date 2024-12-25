package Vue;

import Controller.UserController;
import Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {
    private JFrame frame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private UserController userController;

    public LoginView() {
        userController = new UserController(); // Assurez-vous que UserController est opérationnel
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));

        // Email
        JPanel emailPanel = new JPanel();
        emailPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        emailPanel.add(emailField);

        // Password
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordField);

        // Button
        JButton loginButton = new JButton("Se connecter");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Validation
                User loggedInUser = userController.validateAndGetUser(email, password);
                if (loggedInUser != null) {
                    JOptionPane.showMessageDialog(frame, "Connexion réussie !");
                    frame.dispose(); // Fermer la fenêtre de connexion
                    new MenuView(loggedInUser,userController); // Ouvrir le menu avec l'utilisateur connecté
                } else {
                    JOptionPane.showMessageDialog(frame, "Email ou mot de passe incorrect !");
                }
            }
        });

        // Add components to frame
        frame.add(emailPanel);
        frame.add(passwordPanel);
        frame.add(loginButton);

        frame.setVisible(true);
    }
}

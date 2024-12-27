package Vue;

import controller.UserController;

import javax.swing.*;
import java.awt.*;

public class MenuView {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private UserController userController;

    public MenuView(UserController userController) {
        this.userController = userController;
        initialize();
    }

    private void initialize() {
        // Fenêtre principale
        frame = new JFrame("Menu Principal");
        frame.setSize(1400, 900);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Initialisation de JTabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Onglets
        tabbedPane.addTab("Accueil", createHomePanel());
        tabbedPane.addTab("Gestion des Utilisateurs", createUserManagementPanel());
        tabbedPane.addTab("Statistiques", createStatisticsPanel());

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Onglet "Accueil"
    private JPanel createHomePanel() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(new Color(200, 220, 255));

        JLabel welcomeLabel = new JLabel("Bienvenue dans le Menu Principal", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        homePanel.add(welcomeLabel, BorderLayout.CENTER);

        return homePanel;
    }

    // Onglet "Gestion des Utilisateurs"
    private JPanel createUserManagementPanel() {
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(new Color(230, 240, 255));

        // Intégrer UserView dans cet onglet
        UserView userView = new UserView(userController);
        userPanel.add(userView.getPanel(), BorderLayout.CENTER);

        return userPanel;
    }

    // Onglet "Statistiques"
    private JPanel createStatisticsPanel() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBackground(new Color(255, 250, 200));

        JLabel statsLabel = new JLabel("Section Statistiques", JLabel.CENTER);
        statsLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JTextArea statsArea = new JTextArea("Les statistiques seront affichées ici...");
        statsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        statsArea.setEditable(false);

        statsPanel.add(statsLabel, BorderLayout.NORTH);
        statsPanel.add(new JScrollPane(statsArea), BorderLayout.CENTER);

        return statsPanel;
    }
}

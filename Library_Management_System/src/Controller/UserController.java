package Controller;

import Model.*;
import Util.CSVHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private List<User> users = new ArrayList<>();
    private static final String USER_FILE = "resources/users.csv";
    private int lastUserId = 0;

    public UserController() {
        loadUsersFromFile();
    }

    // Générer un nouvel ID utilisateur unique
    private int generateNewUserId() {
        return ++lastUserId; // Incrémenter et retourner
    }

    // Charger les utilisateurs depuis un fichier CSV
    private void loadUsersFromFile() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            System.out.println("User file does not exist. Initializing with an empty list.");
            return; // Pas d'utilisateur à charger
        }

        try {
            List<String[]> data = CSVHandler.readCSV(USER_FILE);
            for (String[] row : data) {
                try {
                    User user = createUserFromData(row);
                    users.add(user);

                    // Mettre à jour le dernier ID utilisé
                    if (user.getId() > lastUserId) {
                        lastUserId = user.getId();
                    }
                } catch (Exception e) {
                    System.out.println("Skipping malformed line: " + String.join(",", row));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    // Créer un utilisateur à partir des données CSV
    private User createUserFromData(String[] row) {
        int id = Integer.parseInt(row[0]);
        String name = row[1];
        String email = row[2];
        String password = row[3];
        String role = row[4];

        switch (role) {
            case "Member":
                return new Member(id, name, email, password);
            case "Librarian":
                return new Librarian(id, name, email, password);
            case "Admin":
                return new Admin(id, name, email, password);
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }

    // Sauvegarder les utilisateurs dans un fichier CSV
    public void saveUsersToFile() {
        List<String[]> data = new ArrayList<>();
        for (User user : users) {
            data.add(new String[]{
                    String.valueOf(user.getId()),
                    user.getName(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole()
            });
        }
        try {
            CSVHandler.writeCSV(USER_FILE, data);
        } catch (IOException e) {
            System.out.println("Error saving users to file: " + e.getMessage());
        }
    }

    // Ajouter un nouvel utilisateur
    public void addUser(User user) {
        users.add(user);
        saveUsersToFile();
    }

    // Obtenir tous les utilisateurs
    public List<User> getAllUsers() {
        return users;
    }

    // Supprimer un utilisateur
    public void deleteUser(int id) {
        boolean removed = users.removeIf(user -> user.getId() == id);
        if (removed) {
            saveUsersToFile();
            System.out.println("User with ID " + id + " deleted successfully.");
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }

    // Vérifier si un ID existe
    public boolean isIdExists(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // Ajouter un utilisateur avec un ID généré automatiquement
    public void addUserWithGeneratedId(String name, String email, String password, String role) {
        int newId = generateNewUserId(); // Générer automatiquement un nouvel ID
        User user;
        switch (role) {
            case "Member":
                user = new Member(newId, name, email, password);
                break;
            case "Librarian":
                user = new Librarian(newId, name, email, password);
                break;
            case "Admin":
                user = new Admin(newId, name, email, password);
                break;
            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
        addUser(user);
        System.out.println("User with ID " + newId + " added successfully.");
    }


    public int getNextUserId() {
    	return lastUserId + 1;
}
    public boolean validateUser(String email, String password) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));
    }
    
    public User validateAndGetUser(String email, String password) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
    
    public void updateUser(int id, String name, String email, String password, String role) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                if (user instanceof Admin && role.equals("Admin")) {
                    // No role change needed
                } else if (user instanceof Librarian && role.equals("Librarian")) {
                    // No role change needed
                } else if (user instanceof Member && role.equals("Member")) {
                    // No role change needed
                } else {
                    // Handle role change
                    users.remove(user);
                    switch (role) {
                        case "Admin":
                            user = new Admin(id, name, email, password);
                            break;
                        case "Librarian":
                            user = new Librarian(id, name, email, password);
                            break;
                        case "Member":
                            user = new Member(id, name, email, password);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid role");
                    }
                    users.add(user);
                }
                saveUsersToFile();
                return;
            }
        }
        throw new IllegalArgumentException("User with ID " + id + " not found.");
    }

}

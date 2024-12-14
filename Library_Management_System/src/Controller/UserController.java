package Controller;

import Model.*;
import Util.CSVHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserController {
    private List<User> users = new ArrayList<>();
    private static final String USER_FILE = "resources/users.csv";

    public UserController() {
        loadUsersFromFile();
    }

    // Load users from CSV file
    private void loadUsersFromFile() {
        try {
            List<String[]> data = CSVHandler.readCSV(USER_FILE);
            for (String[] row : data) {
                int id = Integer.parseInt(row[0]);
                String name = row[1];
                String email = row[2];
                String password = row[3];
                String role = row[4];

                User user;
                switch (role) {
                    case "Member":
                        user = new Member(id, name, email, password);
                        break;
                    case "Librarian":
                        user = new Librarian(id, name, email, password);
                        break;
                    case "Admin":
                        user = new Admin(id, name, email, password);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown role: " + role);
                }
                users.add(user);
            }
        } catch (IOException e) {
            System.out.println("Error reading users from file: " + e.getMessage());
        }
    }

    // Save users to CSV file
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

    // Add a new user
    public void addUser(User user) {
        users.add(user);
        saveUsersToFile();
    }

    // Get all users
    public List<User> getAllUsers() {
        return users;
    }
}

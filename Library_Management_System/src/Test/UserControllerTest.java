package Test;

import Controller.UserController;
import Model.*;

public class UserControllerTest {
    public static void main(String[] args) {
        // Create UserController
        UserController userController = new UserController();

        // Display all users loaded from CSV
        System.out.println("Users loaded from CSV:");
        for (User user : userController.getAllUsers()) {
            System.out.println(user);
        }

        // Add a new user
        User newUser = new Member(4, "Diana", "diana@example.com", "password999");
        userController.addUser(newUser);
        System.out.println("\nAdded new user: " + newUser);

        // Display all users after addition
        System.out.println("\nUsers after addition:");
        for (User user : userController.getAllUsers()) {
            System.out.println(user);
        }

        // Check CSV file to confirm the new user was saved
    }
}

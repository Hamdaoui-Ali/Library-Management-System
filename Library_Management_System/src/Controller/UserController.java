package controller;

import model.*;
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

    private int generateNewUserId() {
        return ++lastUserId;
    }

    public void addUserWithGeneratedId(String nom, String prenom, String cin, int age, String mail, String telephone, String adresse) {
        int newId = generateNewUserId();
        User user = new Member(newId, nom, prenom, cin, age, mail, telephone, adresse);
        users.add(user);
        saveUsersToFile();
    }

    public void updateUser(int id, String nom, String prenom, String cin, int age, String email, String telephone, String adresse) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setNom(nom);
                user.setPrenom(prenom);
                user.setCin(cin);
                user.setAge(age);
                user.setMail(email);
                user.setTelephone(telephone);
                user.setAdresse(adresse);
                saveUsersToFile();
                return;
            }
        }
        throw new IllegalArgumentException("Utilisateur avec l'ID " + id + " non trouvé.");
    }


    public void deleteUser(int id) {
        boolean removed = users.removeIf(user -> user.getId() == id);
        if (removed) {
            saveUsersToFile();
        } else {
            throw new IllegalArgumentException("Utilisateur avec l'ID " + id + " non trouvé.");
        }
    }

    public List<User> getAllUsers() {
        return users;
    }

    private void loadUsersFromFile() {
        File file = new File(USER_FILE);
        if (!file.exists()) {
            return;
        }
        try {
            List<String[]> data = CSVHandler.readCSV(USER_FILE);
            for (String[] row : data) {
                try {
                    int id = Integer.parseInt(row[0]);
                    String nom = row[1];
                    String prenom = row[2];
                    String cin = row[3];
                    int age = Integer.parseInt(row[4]);
                    String mail = row[5];
                    String telephone = row[6];
                    String adresse = row[7];
                    User user = new Member(id, nom, prenom, cin, age, mail, telephone, adresse);
                    users.add(user);
                    if (id > lastUserId) lastUserId = id;
                } catch (Exception e) {
                    System.out.println("Ligne ignorée : " + String.join(",", row));
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement des utilisateurs : " + e.getMessage());
        }
    }

    private void saveUsersToFile() {
        List<String[]> data = new ArrayList<>();
        for (User user : users) {
            data.add(new String[]{
                    String.valueOf(user.getId()), user.getNom(), user.getPrenom(),
                    user.getCin(), String.valueOf(user.getAge()), user.getMail(),
                    user.getTelephone(), user.getAdresse()
            });
        }
        try {
            CSVHandler.writeCSV(USER_FILE, data);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }
}

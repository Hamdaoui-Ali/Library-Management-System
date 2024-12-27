package model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(int id) {
        users.removeIf(user -> user.getId() == id);
    }

    public List<User> getUsers() {
        return users;
    }
}

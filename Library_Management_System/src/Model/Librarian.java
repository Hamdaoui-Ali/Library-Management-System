package devoir;

public class Librarian extends User {
    public Librarian(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public String getRole() {
        return "Librarian";
    }
}

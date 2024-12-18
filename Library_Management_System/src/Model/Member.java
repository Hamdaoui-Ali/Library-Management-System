package Model;

public class Member extends User {
    public Member(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    @Override
    public String getRole() {
        return "Member";
    }
}

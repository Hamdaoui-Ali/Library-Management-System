package model;

public abstract class User {
    private int id;
    private String nom;
    private String prenom;
    private String cin;
    private int age;
    private String mail;
    private String telephone;
    private String adresse;

    public User(int id, String nom, String prenom, String cin, int age, String mail, String telephone, String adresse) {
        if (!isValidEmail(mail)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.cin = cin;
        this.age = age;
        this.mail = mail;
        this.telephone = telephone;
        this.adresse = adresse;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getMail() { return mail; }
    public void setMail(String mail) {
        if (!isValidEmail(mail)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.mail = mail;
    }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    private boolean isValidEmail(String mail) {
        return mail != null && mail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
}

package devoir;

	public abstract class User {
	    private int id;
	    private String name;
	    private String email;
	    private String password; // Hashed password

	    // Constructor
	    public User(int id, String name, String email, String password) {
	        if (!isValidEmail(email)) {
	            throw new IllegalArgumentException("Invalid email format");
	        }
	        this.id = id;
	        this.name = name;
	        this.email = email;
	        this.password = password;
	    }

	    // Getters and Setters
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        if (!isValidEmail(email)) {
	            throw new IllegalArgumentException("Invalid email format");
	        }
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    // Abstract method for specific behavior
	    public abstract String getRole();

	    @Override
	    public String toString() {
	        return "User{" +
	                "id=" + id +
	                ", name='" + name + '\'' +
	                ", email='" + email + '\'' +
	                ", role=" + getRole() +
	                '}';
	    }

	    // Helper method for email validation
	    private boolean isValidEmail(String email) {
	        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
	    }
	}



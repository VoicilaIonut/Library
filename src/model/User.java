package model;


public class User {
    protected static int idCounter = 0;
    protected String id;
    protected String name;
    protected String email;

    public User(String name, String email) {
        this.id = String.valueOf(++idCounter);
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }
}



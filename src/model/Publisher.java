package model;

public class Publisher {
    protected static int idCounter = 0;
    protected String id;
    protected String name;
    protected String email;

    public Publisher(String name, String email) {
        this.id = String.valueOf(++idCounter);
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return "Publisher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }
}

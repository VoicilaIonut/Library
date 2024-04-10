package model;

public class Author {
    protected static int idCounter = 0;
    protected String id;
    protected String name;
    protected String email;

    public Author(String name, String email) {
        this.id = String.valueOf(++idCounter);
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return "Author{" +
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

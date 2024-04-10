package model;


public class User {
    protected static int idCounter = 0;
    protected String id;
    protected String name;
    protected String email;
    protected String phone;
    protected String address;

    public User(String name, String email, String phone, String address) {
        this.id = String.valueOf(++idCounter);
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}



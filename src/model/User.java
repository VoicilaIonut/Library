package model;

public class User {
  protected static int idCounter = 0;
  protected int id;
  protected String name;
  protected String email;

  public User(String name, String email) {
    this.id = ++idCounter;
    this.name = name;
    this.email = email;
  }

  public User(int id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public String toString() {
    return "User{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", email='"
        + email
        + '\''
        + '}';
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }
}

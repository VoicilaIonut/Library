package model;

public class Publisher {
  protected static int idCounter = 0;
  protected int id;
  protected String name;
  protected String email;

  public Publisher(String name, String email) {
    this.id = ++idCounter;
    this.name = name;
    this.email = email;
  }

  public Publisher(int id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public String toString() {
    return "Publisher{"
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

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public int getId() {
    return this.id;
  }
}

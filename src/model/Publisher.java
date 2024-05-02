package model;

public class Publisher {
  protected int id;
  protected String name;
  protected String email;

  public Publisher(String name, String email) {
    this.id = -1;
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

  public void setId(int id) {
    this.id = id;
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

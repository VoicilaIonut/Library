package model;

public class Author {
  protected int id;
  protected String name;
  protected String email;

  public Author(String name, String email) {
    this.id = -1;
    this.name = name;
    this.email = email;
  }

  public Author(int id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public String toString() {
    return "Author{"
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

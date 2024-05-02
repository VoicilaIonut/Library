package model;

public class Category {
  protected int id;
  protected String name;
  protected String description;

  public Category(String name, String description) {
    this.id = -1;
    this.name = name;
    this.description = description;
  }

  public Category(int id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String toString() {
    return "Category{"
        + "id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", description='"
        + description
        + '\''
        + '}';
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public int getId() {
    return this.id;
  }
}

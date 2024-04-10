package model;

public class Category {
    protected static int idCounter = 0;
    protected String id;
    protected String name;
    protected String description;

    public Category(String name, String description) {
        this.id = String.valueOf(++idCounter);
        this.name = name;
        this.description = description;
    }

    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}

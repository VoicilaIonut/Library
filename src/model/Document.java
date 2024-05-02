package model;

public class Document {
  protected int id;
  protected String title;
  protected Author author;
  protected int authorId;
  protected Category category;
  protected int categoryId;
  protected int year;
  protected int pages;

  public Document(String title, Author author, Category category, int year, int pages) {
    this.id = -1;
    this.title = title;
    this.author = author;
    this.authorId = author.getId();
    this.category = category;
    this.categoryId = category.getId();
    this.year = year;
    this.pages = pages;
  }

  public Document(int id, String title, int authorId, int categoryId, int year, int pages) {
    this.id = id;
    this.title = title;
    this.authorId = authorId;
    this.categoryId = categoryId;
    this.year = year;
    this.pages = pages;
  }

  public String toString() {
    return "Document{"
        + "id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + ", authorId='"
        + authorId
        + '\''
        + ", categoryId='"
        + categoryId
        + '\''
        + ", year="
        + year
        + ", pages="
        + pages
        + '}';
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public Author getAuthor() {
    return this.author;
  }

  public Category getCategory() {
    return this.category;
  }

  public int getYear() {
    return this.year;
  }

  public int getAuthorId() {
    return this.authorId;
  }

  public int getCategoryId() {
    return this.categoryId;
  }

  public int getPages() {
    return this.pages;
  }
}

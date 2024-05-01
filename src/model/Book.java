package model;

public class Book extends Document {
  protected String ISBN;
  protected Publisher publisher;
  protected int copies;
  protected int loans_count;

  public Book(
      String title,
      Author author,
      String ISBN,
      Category category,
      Publisher publisher,
      int year,
      int pages,
      int copies) {
    super(title, author, category, year, pages);
    this.ISBN = ISBN;
    this.publisher = publisher;
    this.copies = copies;
    this.loans_count = 0;
  }

  public String toString() {
    return "Book{"
        + "id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + ", author='"
        + author
        + '\''
        + ", ISBN='"
        + ISBN
        + '\''
        + ", category='"
        + category
        + '\''
        + ", publisher='"
        + publisher
        + '\''
        + ", year="
        + year
        + ", pages="
        + pages
        + ", copies="
        + copies
        + ", loans_count="
        + loans_count
        + '}';
  }

  public int getCopies() {
    return this.copies;
  }

  public int getNumberOfLoans() {
    return this.loans_count;
  }

  public void loan() {
    this.copies--;
    this.loans_count++;
  }

  public void returnBook() {
    this.copies++;
  }
}

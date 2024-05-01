package model;

public class Book extends Document {
  protected String ISBN;
  protected Publisher publisher;
  protected int publisherId;
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

  public Book(
      int id,
      String title,
      int authorId,
      int categoryId,
      int year,
      int pages,
      String ISBN,
      int publisherId,
      int copies,
      int loans_count) {
    super(id, title, authorId, categoryId, year, pages);
    this.ISBN = ISBN;
    this.publisherId = publisherId;
    this.copies = copies;
    this.loans_count = loans_count;
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

  public String getISBN() {
    return this.ISBN;
  }

  public int getPublisherId() {
    return this.publisherId;
  }

  public int getLoansCount() {
    return this.loans_count;
  }
}

package model;


public class Loan {
  protected int id;
  protected int bookId;
  protected Book book;
  protected int userId;
  protected User user;
  protected String date;
  protected String dueDate;
  protected String returnDate;
  protected boolean returned;

  public Loan(Book book, User user, String date, String dueDate) {
    this.id = -1;
    this.book = book;
    this.bookId = book.getId();
    this.user = user;
    this.userId = user.getId();
    this.date = date;
    this.dueDate = dueDate;
    this.returnDate = "";
    this.returned = false;
  }

  public Loan(
      int id,
      int bookId,
      int userId,
      String date,
      String dueDate,
      String returnDate,
      boolean returned) {
    this.id = id;
    this.bookId = bookId;
    this.userId = userId;
    this.date = date;
    this.dueDate = dueDate;
    this.returnDate = returnDate;
    this.returned = returned;
  }

  public String toString() {
    return "Loan{"
        + "id='"
        + id
        + '\''
        + ", bookId='"
        + bookId
        + '\''
        + ", userId='"
        + userId
        + '\''
        + ", date='"
        + date
        + '\''
        + ", dueDate='"
        + dueDate
        + '\''
        + ", returnDate='"
        + returnDate
        + '\''
        + ", returned="
        + returned
        + '}';
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setReturnDate(String returnDate) {
    this.returnDate = returnDate;
  }

  public void setReturned(boolean returned) {
    this.returned = returned;
  }

  public int getId() {
    return this.id;
  }

  public Book getBook() {
    return this.book;
  }

  public int getBookId() {
    return this.bookId;
  }

  public User getUser() {
    return this.user;
  }

  public int getUserId() {
    return this.userId;
  }


  public boolean isCompleted() {
    return this.returned;
  }

  public String getDate() {
    return this.date;
  }

  public String getDueDate() {
    return this.dueDate;
  }

  public String getReturnDate() {
    return this.returnDate;
  }

  public boolean isReturned() {
    return this.returned;
  }
}

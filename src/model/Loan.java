package model;

import java.util.Date;

public class Loan {
  protected static int idCounter = 0;
  protected int id;
  protected int bookId;
  protected Book book;
  protected int userId;
  protected User user;
  protected String date;
  protected String dueDate;
  protected String returnDate;
  protected boolean returned;
  protected boolean extended;

  public Loan(Book book, User user, String date, String dueDate) {
    this.id = ++idCounter;
    this.book = book;
    this.user = user;
    this.date = date;
    this.dueDate = dueDate;
    this.returnDate = "";
    this.returned = false;
    this.extended = false;
  }

  public Loan(
      int id,
      int bookId,
      int userId,
      String date,
      String dueDate,
      String returnDate,
      boolean returned,
      boolean extended) {
    this.id = id;
    this.bookId = bookId;
    this.userId = userId;
    this.date = date;
    this.dueDate = dueDate;
    this.returnDate = returnDate;
    this.returned = returned;
    this.extended = extended;
  }

  public String toString() {
    return "Loan{"
        + "id='"
        + id
        + '\''
        + ", book='"
        + book
        + '\''
        + ", user='"
        + user
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
        + ", extended="
        + extended
        + '}';
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

  public void completeLoan() {
    this.book.returnBook();
    this.returned = true;
    this.returnDate = new Date().toString();
    this.book.returnBook();
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

  public boolean isExtended() {
    return this.extended;
  }
}

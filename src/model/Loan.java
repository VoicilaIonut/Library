package model;


import java.util.Date;

public class Loan {
    protected static int idCounter = 0;
    protected String id;
    protected Book book;
    protected User user;
    protected String date;
    protected String dueDate;
    protected String returnDate;
    protected boolean returned;
    protected boolean extended;

    public Loan(Book book, User user, String date, String dueDate) {
        this.id = String.valueOf(++idCounter);
        this.book = book;
        this.user = user;
        this.date = date;
        this.dueDate = dueDate;
        this.returnDate = "";
        this.returned = false;
        this.extended = false;
        book.loan();
    }

    public String toString() {
        return "Loan{" +
                "id='" + id + '\'' +
                ", book='" + book + '\'' +
                ", user='" + user + '\'' +
                ", date='" + date + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", returned=" + returned +
                ", extended=" + extended +
                '}';
    }

    public String getId() {
        return this.id;
    }

    public Book getBook() {
        return this.book;
    }

    public User getUser() {
        return this.user;
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

}
package model;

import java.util.Comparator;

public class Document {

    protected static int idCounter = 0;
    protected String id;
    protected String title;
    protected Author author;
    protected Category category;

    protected int year;
    protected int pages;

    public Document(String title, Author author, Category category, int year, int pages) {
        this.id = String.valueOf(++idCounter);
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.pages = pages;
    }

    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", year=" + year +
                ", pages=" + pages +
                '}';
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
}


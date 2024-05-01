package model;

public class Article extends Document {
  protected String journal;
  protected int volume;
  protected int number;

  public Article(
      String title,
      Author author,
      Category category,
      String journal,
      int volume,
      int number,
      int year,
      int pages) {
    super(title, author, category, year, pages);
    this.journal = journal;
    this.volume = volume;
    this.number = number;
  }

  public String toString() {
    return "Article{"
        + "id='"
        + id
        + '\''
        + ", title='"
        + title
        + '\''
        + ", author='"
        + author
        + '\''
        + ", category='"
        + category
        + '\''
        + ", year="
        + year
        + ", pages="
        + pages
        + ", journal='"
        + journal
        + '\''
        + ", volume="
        + volume
        + ", number="
        + number
        + '}';
  }

  public String getJournal() {
    return this.journal;
  }

  public int getVolume() {
    return this.volume;
  }

  public int getNumber() {
    return this.number;
  }
}

package repository;

import model.Author;

import java.util.ArrayList;
import java.util.List;

public class DefaultAuthorRepository implements AuthorRepository {
  List<Author> authors;

  public DefaultAuthorRepository() {
    this.authors = new ArrayList<>();
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public void addAuthor(Author author) {
    authors.add(author);
  }

  public Author getAuthorOrCreate(String name, String email) {
    for (Author a : authors) {
      if (a.getName().equals(name) && a.getEmail().equals(email)) {
        return a;
      }
    }
    Author author = new Author(name, email);
    addAuthor(author);
    return author;
  }
}

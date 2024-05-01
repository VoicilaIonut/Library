package repository;

import model.Author;
import java.util.List;

public interface AuthorRepository {
  List<Author> getAuthors();

  void addAuthor(Author author);

  Author getAuthorOrCreate(String name, String email);
}

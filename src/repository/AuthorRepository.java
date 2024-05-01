package repository;

import model.Author;
import model.Response;

import java.util.List;

public interface AuthorRepository {
  List<Author> getAuthors();

  Response addAuthor(Author author);

  Author getAuthorOrCreate(String name, String email);

  Author getAuthorById(int id);
}

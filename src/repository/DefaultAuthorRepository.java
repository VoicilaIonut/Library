package repository;

import model.Author;
import model.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultAuthorRepository implements AuthorRepository {

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DbConstants.URL);
  }

  public List<Author> getAuthors() {
    String query = "SELECT * FROM Author";
    List<Author> authors = new ArrayList<>();
    try {
      Connection conn = getConnection();
      PreparedStatement stmt = conn.prepareStatement(query);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        Author author = new Author(id, name, email);
        authors.add(author);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return authors;
  }

  public Author getAuthorById(int id) {
    String query = "SELECT * FROM Author WHERE id = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        int authorId = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        return new Author(authorId, name, email);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Response addAuthor(Author author) {
    String query = "INSERT INTO Author (name, email) VALUES (?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, author.getName());
      stmt.setString(2, author.getEmail());
      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating author failed, no rows affected.");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          author.setId(id);
          return new Response(true, "Author added successfully.", author);
        } else {
          throw new SQLException("Creating author failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add author. " + e.getMessage());
    }
  }

  public Author getAuthorOrCreate(String name, String email) {
    Author author =
        getAuthors().stream()
            .findFirst()
            .filter(a -> a.getName().equals(name) && a.getEmail().equals(email))
            .orElse(null);
    if (author == null) {
      Response response = addAuthor(new Author(name, email));
      if (response.isSuccess()) {
        return (Author) response.getData();
      } else {
        return null;
      }
    }
    return author;
  }
}

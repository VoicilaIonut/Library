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
        String name = rs.getString("name");
        String email = rs.getString("email");
        Author author = new Author(name, email);
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
        String name = rs.getString("name");
        String email = rs.getString("email");
        return new Author(id, name, email);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Response addAuthor(Author author) {
    String query = "INSERT INTO Author (name, email) VALUES (?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, author.getName());
      stmt.setString(2, author.getEmail());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add author." + e.getMessage());
    }
    return new Response(true, "Author added successfully.");
  }

  public Author getAuthorOrCreate(String name, String email) {
    for (Author a : getAuthors()) {
      if (a.getName().equals(name) && a.getEmail().equals(email)) {
        return a;
      }
    }
    Author author = new Author(name, email);
    Response response = addAuthor(author);
    if (!response.isSuccess()) {
      return null;
    }
    return author;
  }
}

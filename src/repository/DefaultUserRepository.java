package repository;

import model.Response;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultUserRepository implements UserRepository {
  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DbConstants.URL);
  }

  public List<User> getUsers() {
    String query = "SELECT * FROM User";
    List<User> users = new ArrayList<>();

    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        User user = new User(id, name, email);
        users.add(user);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    return users;
  }

  public User getUserByEmail(String email) {
    return getUsers().stream()
        .filter(user -> user.getEmail().equals(email))
        .findFirst()
        .orElse(null);
  }

  public Response addUser(User user) {
    String query = "INSERT INTO User (name, email) VALUES (?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, user.getName());
      stmt.setString(2, user.getEmail());
      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating user failed, no rows affected.");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          user.setId(id);
          return new Response(true, "User added successfully.", user);
        } else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add user." + e.getMessage());
    }
  }
}

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
    for (User user : getUsers()) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;
  }

  public Response addUser(User user) {
    String query = "INSERT INTO User (id, name, email) VALUES (?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, user.getId());
      stmt.setString(2, user.getName());
      stmt.setString(3, user.getEmail());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add user." + e.getMessage());
    }
    return new Response(true, "User added successfully");
  }
}

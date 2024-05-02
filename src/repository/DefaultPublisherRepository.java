package repository;

import model.Publisher;
import model.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DefaultPublisherRepository implements PublisherRepository {
  List<Publisher> publishers;

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DbConstants.URL);
  }

  public DefaultPublisherRepository() {
    publishers = new ArrayList<>();
  }

  public List<Publisher> getPublishers() {
    String query = "SELECT * FROM Publisher";
    List<Publisher> publishers = new ArrayList<>();

    try (Connection conn = getConnection();
        var stmt = conn.prepareStatement(query);
        var rs = stmt.executeQuery()) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        Publisher publisher = new Publisher(id, name, email);
        publishers.add(publisher);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    return publishers;
  }

  public Response addPublisher(Publisher publisher) {
    String query = "INSERT INTO Publisher (name, email) VALUES (?, ?)";
    try (Connection conn = getConnection();
        var stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, publisher.getName());
      stmt.setString(2, publisher.getEmail());
      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating user failed, no rows affected.");
      }
      try (var generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          publisher.setId(id);
          return new Response(true, "Publisher added successfully.", publisher);
        } else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add the publisher." + e.getMessage());
    }
  }

  public Publisher getPublisherOrCreate(String name, String email) {
    Publisher publisher =
        getPublishers().stream()
            .filter(p -> p.getName().equals(name) && p.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    if (publisher == null) {
      Response response = addPublisher(new Publisher(name, email));
      if (response.isSuccess()) {
        publisher = (Publisher) response.getData();
      } else {
        return null;
      }
    }
    return publisher;
  }
}

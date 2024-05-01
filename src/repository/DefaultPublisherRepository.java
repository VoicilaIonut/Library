package repository;

import model.Publisher;
import model.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
    String query = "INSERT INTO Publisher (id, name, email) VALUES (?, ?, ?)";
    try (Connection conn = getConnection();
        var stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, publisher.getId());
      stmt.setString(2, publisher.getName());
      stmt.setString(3, publisher.getEmail());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add the publisher." + e.getMessage());
    }
    return new Response(true, "Publisher added successfully.");
  }

  public Publisher getPublisherOrCreate(String name, String email) {
    for (Publisher p : getPublishers()) {
      if (p.getName().equals(name) && p.getEmail().equals(email)) {
        return p;
      }
    }
    Publisher publisher = new Publisher(name, email);
    Response response = addPublisher(publisher);
    if (!response.isSuccess()) {
      return null;
    }
    return publisher;
  }
}

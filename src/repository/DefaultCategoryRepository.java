package repository;

import model.Category;
import model.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultCategoryRepository implements CategoryRepository {

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DbConstants.URL);
  }

  public List<Category> getCategories() {
    String query = "SELECT * FROM Category";
    List<Category> categories = new ArrayList<>();
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        Category category = new Category(id, name, description);
        categories.add(category);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return categories;
  }

  public Response addCategory(Category category) {
    String query = "INSERT INTO Category (name, description) VALUES (?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, category.getName());
      stmt.setString(2, category.getDescription());
      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating category failed, no rows affected.");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          category.setId(id);
          return new Response(true, "Category added successfully.", category);
        } else {
          throw new SQLException("Creating category failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add category. " + e.getMessage());
    }
  }

  public Category getCategoryById(int id) {
    String query = "SELECT * FROM Category WHERE id = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        String name = rs.getString("name");
        String description = rs.getString("description");
        return new Category(id, name, description);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Category getCategoryByNameOrCreate(String name, String description) {
    Category category =
        getCategories().stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
    if (category == null) {
      Response response = addCategory(new Category(name, description));
      if (response.isSuccess()) {
        return (Category) response.getData();
      } else {
        return null;
      }
    }
    return category;
  }
}

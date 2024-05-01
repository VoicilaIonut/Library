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
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, category.getName());
      stmt.setString(2, category.getDescription());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Category could not be added");
    }
    return new Response(true, "Category added successfully");
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
    for (Category c : getCategories()) {
      if (c.getName().equals(name)) {
        return c;
      }
    }
    Category category = new Category(name, description);
    Response response = addCategory(category);
    if (!response.isSuccess()) {
      return null;
    }
    return category;
  }
}

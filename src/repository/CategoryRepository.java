package repository;

import model.Category;
import model.Response;

import java.util.List;

public interface CategoryRepository {
  Response addCategory(Category category);

  List<Category> getCategories();

  Category getCategoryById(int id);

  Category getCategoryByNameOrCreate(String name, String description);
}

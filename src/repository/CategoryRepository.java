package repository;

import model.Category;

import java.util.List;

public interface CategoryRepository {
  void addCategory(Category category);

  List<Category> getCategories();

  Category getCategoryByNameOrCreate(String name, String description);
}

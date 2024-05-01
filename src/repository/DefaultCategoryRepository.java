package repository;

import model.Category;

import java.util.ArrayList;
import java.util.List;

public class DefaultCategoryRepository implements CategoryRepository {
  List<Category> categories;

  public DefaultCategoryRepository() {
    this.categories = new ArrayList<>();
  }

  public List<Category> getCategories() {
    return this.categories;
  }

  public void addCategory(Category category) {
    this.categories.add(category);
  }

  public Category getCategoryByNameOrCreate(String name, String description) {
    for (Category c : getCategories()) {
      if (c.getName().equals(name)) {
        return c;
      }
    }
    Category category = new Category(name, description);
    addCategory(category);
    return category;
  }
}

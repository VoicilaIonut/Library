package repository;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class DefaultUserRepository implements UserRepository {
  List<User> users;

  public DefaultUserRepository() {
    this.users = new ArrayList<>();
  }

  public List<User> getUsers() {
    return this.users;
  }

  public User getUserByEmail(String email) {
    for (User user : getUsers()) {
      if (user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;
  }

  public void addUser(User user) {
    this.users.add(user);
  }
}

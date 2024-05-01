package repository;

import model.User;

import java.util.List;

public interface UserRepository {
  void addUser(User user);

  User getUserByEmail(String email);

  List<User> getUsers();
}

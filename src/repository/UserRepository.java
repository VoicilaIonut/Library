package repository;

import model.Response;
import model.User;

import java.util.List;

public interface UserRepository {
  Response addUser(User user);

  User getUserByEmail(String email);

  List<User> getUsers();
}

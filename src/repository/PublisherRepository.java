package repository;

import model.Publisher;

import java.util.List;

public interface PublisherRepository {
  void addPublisher(Publisher publisher);

  List<Publisher> getPublishers();

  Publisher getPublisherOrCreate(String name, String email);
}

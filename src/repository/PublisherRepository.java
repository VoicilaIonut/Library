package repository;

import model.Publisher;
import model.Response;

import java.util.List;

public interface PublisherRepository {
  Response addPublisher(Publisher publisher);

  List<Publisher> getPublishers();

  Publisher getPublisherOrCreate(String name, String email);
}

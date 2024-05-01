package repository;

import model.Publisher;

import java.util.ArrayList;
import java.util.List;

public class DefaultPublisherRepository implements PublisherRepository {
  List<Publisher> publishers;

  public DefaultPublisherRepository() {
    publishers = new ArrayList<>();
  }

  public List<Publisher> getPublishers() {
    return publishers;
  }

  public void addPublisher(Publisher publisher) {
    publishers.add(publisher);
  }

  public Publisher getPublisherOrCreate(String name, String email) {
    for (Publisher p : getPublishers()) {
      if (p.getName().equals(name) && p.getEmail().equals(email)) {
        return p;
      }
    }
    Publisher publisher = new Publisher(name, email);
    addPublisher(publisher);
    return publisher;
  }
}

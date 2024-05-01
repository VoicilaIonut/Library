package repository;

import model.Author;
import model.Category;
import model.Document;

import java.util.List;
import java.util.PriorityQueue;

public interface DocumentRepository {
  public Document getDocumentByTitle(String title);

  public List<Document> getDocumentsByAuthor(Author author);

  public List<Document> getDocuments();

  public PriorityQueue<Document> getDocumentsByPopularity();

  public List<Document> getDocumentsByCategory(Category category);

  public void addDocument(Document document);
}

package repository;

import model.Author;
import model.Category;
import model.Document;

import java.util.List;
import java.util.PriorityQueue;

public interface DocumentRepository {
  List<Document> getDocumentsByTitle(String title);

  List<Document> getDocumentsByAuthor(Author author);

  List<Document> getDocuments();

  PriorityQueue<Document> getDocumentsByPopularity();

  List<Document> getDocumentsByCategory(Category category);

  Document getDocumentByTitle(String title);

  void addDocument(Document document);
}

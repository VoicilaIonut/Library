package repository;

import model.Author;
import model.Category;
import model.Document;
import model.DocumentComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class DefaultDocumentRepository implements DocumentRepository {
  private final PriorityQueue<Document> documents;

  public DefaultDocumentRepository() {
    this.documents = new PriorityQueue<>(new DocumentComparator());
  }

  public List<Document> getDocuments() {
    return new ArrayList<>(documents);
  }

  public PriorityQueue<Document> getDocumentsByPopularity() {
    return documents;
  }

  public List<Document> getDocumentsByTitle(String title) {
    List<Document> documentsByTitle = new ArrayList<>();
    for (Document d : documents) {
      if (d.getTitle().equals(title)) {
        documentsByTitle.add(d);
      }
    }
    return documentsByTitle;
  }

  public List<Document> getDocumentsByAuthor(Author author) {
    List<Document> documentsByAuthor = new ArrayList<>();
    for (Document document : documents) {
      if (document.getAuthor() == author) {
        documentsByAuthor.add(document);
      }
    }
    return documentsByAuthor;
  }

  public List<Document> getDocumentsByCategory(Category category) {
    List<Document> documentsByCategory = new ArrayList<>();
    for (Document document : documents) {
      if (document.getCategory() == category) {
        documentsByCategory.add(document);
      }
    }
    return documentsByCategory;
  }

  public Document getDocumentByTitle(String title) {
    for (Document d : getDocuments()) {
      if (d.getTitle().equals(title)) {
        return d;
      }
    }
    return null;
  }

  public void addDocument(Document document) {
    documents.offer(document);
  }
}

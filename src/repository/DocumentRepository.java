package repository;

import model.*;

import java.util.List;

public interface DocumentRepository {

  List<Document> getDocumentsByAuthor(Author author);

  public List<Document> getArticles();

  public List<Document> getBooks();

  List<Document> getDocuments();

  List<Document> getDocumentsByCategory(Category category);

  Response addDocument(Document document);

  Response addBook(Book book);

  Response addArticle(Article article);

  List<Document> getAllDocumentsByTitle(String title);

  Document getDocumentByTitle(String title);

}

package repository;

import model.*;

import java.util.List;

public interface DocumentRepository {

  List<Document> getDocumentsByAuthor(Author author);

  List<Document> getArticles();

  List<Document> getBooks();

  List<Document> getDocuments();

  List<Document> getDocumentsByCategory(Category category);

  Response addDocument(Document document);

  Response addBook(Book book);

  Response addArticle(Article article);

  List<Document> getAllDocumentsByTitle(String title);

  Response loanBook(Book book);

  Response completeLoanBook(int book);
}

package repository;

import model.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class DefaultDocumentRepository implements DocumentRepository {
  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DbConstants.URL);
  }

  public List<Document> getBooks() {
    String query = "SELECT * FROM Book";
    List<Document> books = new ArrayList<>();
    try (Connection conn = getConnection();
        var stmt = conn.prepareStatement(query);
        var rs = stmt.executeQuery()) {
      while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int authorId = rs.getInt("authorId");
        int categoryId = rs.getInt("categoryId");
        int year = rs.getInt("year");
        int pages = rs.getInt("pages");
        String ISBN = rs.getString("ISBN");
        int publisherId = rs.getInt("publisherId");
        int copies = rs.getInt("copies");
        int loansCount = rs.getInt("loansCount");
        Book book =
            new Book(
                id,
                title,
                authorId,
                categoryId,
                year,
                pages,
                ISBN,
                publisherId,
                copies,
                loansCount);
        books.add(book);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return books;
  }

  public List<Document> getArticles() {
    String query = "SELECT * FROM Article";
    List<Document> articles = new ArrayList<>();
    try (Connection conn = getConnection();
        var stmt = conn.prepareStatement(query);
        var rs = stmt.executeQuery()) {
      while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int authorId = rs.getInt("authorId");
        int categoryId = rs.getInt("categoryId");
        int year = rs.getInt("year");
        int pages = rs.getInt("pages");
        String journal = rs.getString("journal");
        int volume = rs.getInt("volume");
        int number = rs.getInt("number");
        Article article =
            new Article(id, title, authorId, categoryId, year, pages, journal, volume, number);
        articles.add(article);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return articles;
  }

  public List<Document> getDocuments() {
    String query = "SELECT * FROM Document";
    List<Document> documents = new ArrayList<>();
    try (Connection conn = getConnection();
        var stmt = conn.prepareStatement(query);
        var rs = stmt.executeQuery()) {
      while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int authorId = rs.getInt("authorId");
        int categoryId = rs.getInt("categoryId");
        int year = rs.getInt("year");
        int pages = rs.getInt("pages");
        Document document = new Document(id, title, authorId, categoryId, year, pages);
        documents.add(document);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return documents;
  }

  public PriorityQueue<Document> getAllDocumentsByPopularity() {
    List<Document> documents = getDocuments();
    PriorityQueue<Document> documentsByPopularity = new PriorityQueue<>(new DocumentComparator());
    documentsByPopularity.addAll(documents);
    return documentsByPopularity;
  }

  public Response addDocument(Document document) {
    String query =
        "INSERT INTO Document (id, title, authorId, categoryId, year, pages) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
        var stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, document.getId());
      stmt.setString(2, document.getTitle());
      stmt.setInt(3, document.getAuthorId());
      stmt.setInt(4, document.getCategoryId());
      stmt.setInt(5, document.getYear());
      stmt.setInt(6, document.getPages());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add document." + e.getMessage());
    }
    return new Response(true, "Document added successfully");
  }

  public Response addBook(Book book) {
    String query =
        "INSERT INTO Book (id, title, authorId, categoryId, year, pages, ISBN, publisherId, copies, loansCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
        var stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, book.getId());
      stmt.setString(2, book.getTitle());
      stmt.setInt(3, book.getAuthorId());
      stmt.setInt(4, book.getCategoryId());
      stmt.setInt(5, book.getYear());
      stmt.setInt(6, book.getPages());
      stmt.setString(7, book.getISBN());
      stmt.setInt(8, book.getPublisherId());
      stmt.setInt(9, book.getCopies());
      stmt.setInt(10, book.getLoansCount());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add book." + e.getMessage());
    }
    return new Response(true, "Book added successfully");
  }

  public Response addArticle(Article article) {
    String query =
        "INSERT INTO Article (id, title, authorId, categoryId, year, pages, journal, volume, number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
        var stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, article.getId());
      stmt.setString(2, article.getTitle());
      stmt.setInt(3, article.getAuthorId());
      stmt.setInt(4, article.getCategoryId());
      stmt.setInt(5, article.getYear());
      stmt.setInt(6, article.getPages());
      stmt.setString(7, article.getJournal());
      stmt.setInt(8, article.getVolume());
      stmt.setInt(9, article.getNumber());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add article." + e.getMessage());
    }
    return new Response(true, "Article added successfully");
  }

  public List<Document> getDocumentsByAuthor(Author author) {
    List<Document> documentsByAuthor = new ArrayList<>();
    for (Document document : getDocuments()) {
      if (document.getAuthor() == author) {
        documentsByAuthor.add(document);
      }
    }
    return documentsByAuthor;
  }

  public List<Document> getDocumentsByCategory(Category category) {
    List<Document> documentsByCategory = new ArrayList<>();
    for (Document document : getDocuments()) {
      if (document.getCategory() == category) {
        documentsByCategory.add(document);
      }
    }
    return documentsByCategory;
  }

  public List<Document> getAllDocumentsByTitle(String title) {
    List<Document> documentsByTitle = new ArrayList<>();
    for (Document d : getDocuments()) {
      if (d.getTitle().equals(title)) {
        documentsByTitle.add(d);
      }
    }
    for (Document d : getBooks()) {
      if (d.getTitle().equals(title)) {
        documentsByTitle.add(d);
      }
    }
    for (Document d : getArticles()) {
      if (d.getTitle().equals(title)) {
        documentsByTitle.add(d);
      }
    }
    return documentsByTitle;
  }

  public Document getDocumentByTitle(String title) {
    for (Document d : getDocuments()) {
      if (d.getTitle().equals(title)) {
        return d;
      }
    }
    return null;
  }
}

package repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.*;

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

  public Response addDocument(Document document) {
    String query =
        "INSERT INTO Document (title, authorId, categoryId, year, pages) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, document.getTitle());
      stmt.setInt(2, document.getAuthorId());
      stmt.setInt(3, document.getCategoryId());
      stmt.setInt(4, document.getYear());
      stmt.setInt(5, document.getPages());
      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating document failed, no rows affected.");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          Document newDocument =
              new Document(
                  id,
                  document.getTitle(),
                  document.getAuthorId(),
                  document.getCategoryId(),
                  document.getYear(),
                  document.getPages());
          return new Response(true, "Document added successfully.", newDocument);
        } else {
          throw new SQLException("Creating document failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add document. " + e.getMessage());
    }
  }

  public Response addBook(Book book) {
    String query =
        "INSERT INTO Book (title, authorId, categoryId, year, pages, ISBN, publisherId, copies, loansCount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, book.getTitle());
      stmt.setInt(2, book.getAuthorId());
      stmt.setInt(3, book.getCategoryId());
      stmt.setInt(4, book.getYear());
      stmt.setInt(5, book.getPages());
      stmt.setString(6, book.getISBN());
      stmt.setInt(7, book.getPublisherId());
      stmt.setInt(8, book.getCopies());
      stmt.setInt(9, book.getLoansCount());
      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating book failed, no rows affected.");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          Book newBook =
              new Book(
                  id,
                  book.getTitle(),
                  book.getAuthorId(),
                  book.getCategoryId(),
                  book.getYear(),
                  book.getPages(),
                  book.getISBN(),
                  book.getPublisherId(),
                  book.getCopies(),
                  book.getLoansCount());
          return new Response(true, "Book added successfully.", newBook);
        } else {
          throw new SQLException("Creating book failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add book. " + e.getMessage());
    }
  }

  public Response addArticle(Article article) {
    String query =
        "INSERT INTO Article (title, authorId, categoryId, year, pages, journal, volume, number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setString(1, article.getTitle());
      stmt.setInt(2, article.getAuthorId());
      stmt.setInt(3, article.getCategoryId());
      stmt.setInt(4, article.getYear());
      stmt.setInt(5, article.getPages());
      stmt.setString(6, article.getJournal());
      stmt.setInt(7, article.getVolume());
      stmt.setInt(8, article.getNumber());
      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating article failed, no rows affected.");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          Article newArticle =
              new Article(
                  id,
                  article.getTitle(),
                  article.getAuthorId(),
                  article.getCategoryId(),
                  article.getYear(),
                  article.getPages(),
                  article.getJournal(),
                  article.getVolume(),
                  article.getNumber());
          return new Response(true, "Article added successfully.", newArticle);
        } else {
          throw new SQLException("Creating article failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add article. " + e.getMessage());
    }
  }

  public List<Document> getDocumentsByAuthor(Author author) {
    return getDocuments().stream()
        .filter(document -> document.getAuthorId() == author.getId())
        .toList();
  }

  public List<Document> getDocumentsByCategory(Category category) {
    return getDocuments().stream()
        .filter(document -> document.getCategoryId() == category.getId())
        .toList();
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

  public Response loanBook(Book book) {
    String query = "UPDATE Book SET copies = copies - 1, loansCount = loansCount + 1 WHERE id = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, book.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to loan book." + e.getMessage());
    }
    return new Response(true, "Book loaned successfully");
  }

  public Response completeLoanBook(int id) {
    String query = "UPDATE Book SET copies = copies + 1 WHERE id = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to complete loan book." + e.getMessage());
    }
    return new Response(true, "Book loan completed successfully");
  }
}

package services;

import java.util.ArrayList;
import java.util.List;
import model.*;
import repository.*;

public class LibraryService {
  DocumentRepository documentRepository = new DefaultDocumentRepository();
  AuthorRepository authorRepository = new DefaultAuthorRepository();
  UserRepository userRepository = new DefaultUserRepository();
  PublisherRepository publisherRepository = new DefaultPublisherRepository();
  LoanRepository loanRepository = new DefaultLoanRepository();
  CategoryRepository categoryRepository = new DefaultCategoryRepository();

  public Response addDocument(
      String authorName,
      String authorEmail,
      String categoryName,
      String documentTitle,
      int documentYear,
      int documentPages) {
    Author author = authorRepository.getAuthorOrCreate(authorName, authorEmail);
    Category category = categoryRepository.getCategoryByNameOrCreate(categoryName, "");
    documentRepository.addDocument(
        new Document(documentTitle, author, category, documentYear, documentPages));
    return new Response(true, "Document added successfully");
  }

  public Response addBook(
      String authorName,
      String authorEmail,
      String publisherName,
      String publisherEmail,
      String categoryName,
      String documentTitle,
      int documentYear,
      int documentPages,
      String ISBN,
      int copies) {
    Author author = authorRepository.getAuthorOrCreate(authorName, authorEmail);
    Publisher publisher = publisherRepository.getPublisherOrCreate(publisherName, publisherEmail);
    Category category = categoryRepository.getCategoryByNameOrCreate(categoryName, "");
    documentRepository.addDocument(
        new Book(
            documentTitle, author, ISBN, category, publisher, documentYear, documentPages, copies));
    return new Response(true, "Book added successfully");
  }

  public Response addArticle(
      String authorName,
      String authorEmail,
      String categoryName,
      String documentTitle,
      int documentYear,
      int documentPages,
      String journal,
      int volume,
      int number) {
    Author author = authorRepository.getAuthorOrCreate(authorName, authorEmail);
    Category category = categoryRepository.getCategoryByNameOrCreate(categoryName, "");
    documentRepository.addDocument(
        new Article(
            documentTitle, author, category, journal, volume, number, documentYear, documentPages));
    return new Response(true, "Article added successfully");
  }

  public List<Document> getDocumentsByAuthor(String authorName) {
    Author author = authorRepository.getAuthorOrCreate(authorName, "");
    return documentRepository.getDocumentsByAuthor(author);
  }

  public List<Document> getDocumentsByCategory(String categoryName) {
    Category category = categoryRepository.getCategoryByNameOrCreate(categoryName, "");
    return documentRepository.getDocumentsByCategory(category);
  }

  public List<Document> getDocumentsByTitle(String title) {
    return documentRepository.getDocumentsByTitle(title);
  }

  public List<Document> getAllDocumentsByPopularity() {
    return documentRepository.getDocumentsByPopularity().stream().toList();
  }

  public List<Book> getBooks() {
    List<Book> books = new ArrayList<>();
    for (Document document : documentRepository.getDocuments()) {
      if (document instanceof Book) {
        books.add((Book) document);
      }
    }
    return books;
  }

  public List<Article> getArticles() {
    List<Article> articles = new ArrayList<>();
    for (Document document : documentRepository.getDocuments()) {
      if (document instanceof Article) {
        articles.add((Article) document);
      }
    }
    return articles;
  }

  public List<Publisher> getPublishers() {
    return publisherRepository.getPublishers();
  }

  public List<Author> getAuthors() {
    return authorRepository.getAuthors();
  }

  public Response addUser(String name, String email) {
    if (userRepository.getUserByEmail(email) == null) {
      userRepository.addUser(new User(name, email));
      return new Response(true, "User added successfully");
    }
    return new Response(false, "User already exists");
  }

  public Response addLoan(String userEmail, String documentTitle) {
    User user = userRepository.getUserByEmail(userEmail);
    if (user == null) {
      return new Response(false, "User not found");
    }
    Document document = documentRepository.getDocumentByTitle(documentTitle);
    if (document == null) {
      return new Response(false, "Document not found");
    }

    if (document instanceof Book book) {
      if (book.getCopies() == 0) {
        return new Response(false, "No copies available");
      }
    } else {
      return new Response(false, "Only books can be loaned");
    }
    loanRepository.addLoan(new Loan((Book) document, user, "date", "dueDate"));
    return new Response(true, "Loan added");
  }

  public Response completeLoan(String userEmail, String documentTitle) {
    User user = userRepository.getUserByEmail(userEmail);
    if (user == null) {
      return new Response(false, "User not found");
    }
    Document document = documentRepository.getDocumentByTitle(documentTitle);
    if (document == null) {
      return new Response(false, "Document not found");
    }
    Loan loan = loanRepository.getLoan(user, (Book) document);
    if (loan == null) {
      return new Response(false, "Loan not found");
    } else if (loan.isCompleted()) {
      return new Response(false, "Loan already completed");
    }
    loan.completeLoan();
    return new Response(true, "Loan completed");
  }
}

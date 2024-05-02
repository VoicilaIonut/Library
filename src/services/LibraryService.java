package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import model.*;
import repository.*;

public class LibraryService {
  DocumentRepository documentRepository;
  AuthorRepository authorRepository;
  UserRepository userRepository;
  PublisherRepository publisherRepository;
  LoanRepository loanRepository;
  CategoryRepository categoryRepository;

  public LibraryService(
      DocumentRepository documentRepository,
      AuthorRepository authorRepository,
      UserRepository userRepository,
      PublisherRepository publisherRepository,
      LoanRepository loanRepository,
      CategoryRepository categoryRepository) {
    this.documentRepository = documentRepository;
    this.authorRepository = authorRepository;
    this.userRepository = userRepository;
    this.publisherRepository = publisherRepository;
    this.loanRepository = loanRepository;
    this.categoryRepository = categoryRepository;
  }

  public Response addDocument(
      String authorName,
      String authorEmail,
      String categoryName,
      String documentTitle,
      int documentYear,
      int documentPages) {
    Author author = authorRepository.getAuthorOrCreate(authorName, authorEmail);
    Category category = categoryRepository.getCategoryByNameOrCreate(categoryName, "");
    if (author == null || category == null) {
      return new Response(false, "Author or category failed to be created");
    }
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
    if (author == null || publisher == null || category == null) {
      return new Response(false, "Author, publisher or category failed to be created");
    }
    documentRepository.addBook(
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
    if (author == null || category == null) {
      return new Response(false, "Author or category failed to be created");
    }
    documentRepository.addArticle(
        new Article(
            documentTitle, author, category, journal, volume, number, documentYear, documentPages));
    return new Response(true, "Article added successfully");
  }

  public List<Document> getDocumentsByAuthor(int id) {
    Author author = authorRepository.getAuthorById(id);
    if (author == null) {
      return null;
    }
    return documentRepository.getDocumentsByAuthor(author);
  }

  public List<Document> getDocumentsByCategory(int id) {
    Category category = categoryRepository.getCategoryById(id);
    if (category == null) {
      return null;
    }
    return documentRepository.getDocumentsByCategory(category);
  }

  public List<Document> getAllDocumentsByTitle(String title) {
    return documentRepository.getAllDocumentsByTitle(title);
  }

  public PriorityQueue<Document> getAllDocumentsByPopularity() {
    PriorityQueue<Document> documentsByPopularity = new PriorityQueue<>(new DocumentComparator());
    List<Document> aux = documentRepository.getDocuments();
    if (aux == null) {
      return null;
    }
    documentsByPopularity.addAll(aux);
    aux = documentRepository.getArticles();
    if (aux == null) {
      return null;
    }
    documentsByPopularity.addAll(aux);
    aux = documentRepository.getBooks();
    if (aux == null) {
      return null;
    }
    documentsByPopularity.addAll(aux);
    return documentsByPopularity;
  }

  public List<Book> getBooks() {
    List<Book> books = new ArrayList<>();
    for (Document document : documentRepository.getBooks()) {
      if (document instanceof Book) {
        books.add((Book) document);
      }
    }
    return books;
  }

  public List<Article> getArticles() {
    List<Article> articles = new ArrayList<>();
    for (Document document : documentRepository.getArticles()) {
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
      return userRepository.addUser(new User(name, email));
    }
    return new Response(false, "User already exists");
  }

  public Response addLoan(String userEmail, String documentTitle) {
    User user = userRepository.getUserByEmail(userEmail);
    if (user == null) {
      return new Response(false, "User not found");
    }
    Book book =
        documentRepository.getBooks().stream()
            .filter(document -> document.getTitle().equals(documentTitle))
            .map(document -> (Book) document)
            .filter(book1 -> book1.getCopies() > 0)
            .findFirst()
            .orElse(null);

    if (book == null) {
      return new Response(false, "Document not available at this time");
    }

    Response loanResponse = loanRepository.addLoan(new Loan(book, user, "date", "dueDate"));
    if (!loanResponse.isSuccess()) {
      return loanResponse;
    }
    Response bookResponse = documentRepository.loanBook(book);
    if (!bookResponse.isSuccess()) {
      // This may fail, but we don't want to keep the loan if something went wrong.
      loanRepository.deleteLoan(((Loan) loanResponse.getData()).getId());
      return bookResponse;
    }
    return new Response(true, "Loan added successfully", loanResponse.getData());
  }

  public Response completeLoan(int id) {
    Loan loan = loanRepository.getLoan(id);
    if (loan == null) {
      return new Response(false, "Loan not found");
    }
    if (loan.isCompleted()) {
      return new Response(false, "Loan already completed");
    }

    Response bookResponse = documentRepository.completeLoanBook(loan.getBookId());
    if (!bookResponse.isSuccess()) {
      return bookResponse;
    }

    loan.setReturnDate(Date.from(new Date().toInstant()).toString());
    loan.setReturned(true);

    loanRepository.completeLoan(loan);
    return new Response(true, "Loan completed successfully");
  }

  public List<User> getUsers() {
    return userRepository.getUsers();
  }

  public List<Loan> getLoans() {
    return loanRepository.getLoans();
  }

  public List<Category> getCategories() {
    return categoryRepository.getCategories();
  }

  public List<Document> getDocuments() {
    return documentRepository.getDocuments();
  }
}

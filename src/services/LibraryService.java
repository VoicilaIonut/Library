package services;

import model.*;
import repository.DefaultDocumentRepository;
import repository.DocumentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Database {
  public static Database instance = null;
  public PriorityQueue<Document> documents;
  public List<User> users;
  public List<Loan> loans;
  public List<Author> authors;
  public List<Category> categories;
  public List<Publisher> publishers;

  private Database() {
    this.documents = new PriorityQueue<>(new DocumentComparator());
    this.users = new ArrayList<>();
    this.loans = new ArrayList<>();
    this.authors = new ArrayList<>();
    this.categories = new ArrayList<>();
    this.publishers = new ArrayList<>();
  }

  public static Database getInstance() {
    if (instance == null) {
      instance = new Database();
    }
    return instance;
  }

  public Author getAuthorOrCreate(String name, String email) {
    for (Author a : authors) {
      if (a.getName().equals(name) && a.getEmail().equals(email)) {
        return a;
      }
    }
    authors.add(new Author(name, email));
    return authors.get(authors.size() - 1);
  }

  public Category getCategoryByNameOrCreate(String name, String description) {
    for (Category c : categories) {
      if (c.getName().equals(name)) {
        return c;
      }
    }
    categories.add(new Category(name, description));
    return categories.get(categories.size() - 1);
  }

  public Publisher getPublisherOrCreate(String name, String email) {
    for (Publisher p : publishers) {
      if (p.getName().equals(name) && p.getEmail().equals(email)) {
        return p;
      }
    }
    publishers.add(new Publisher(name, email));
    return publishers.get(publishers.size() - 1);
  }

  public User getUserByEmail(String email) {
    for (User u : users) {
      if (u.getEmail().equals(email)) {
        return u;
      }
    }
    return null;
  }

  public Document getDocumentByTitle(String title) {
    for (Document d : documents) {
      if (d.getTitle().equals(title)) {
        return d;
      }
    }
    return null;
  }

  public Loan getLoan(User user, Book book) {
    for (Loan l : loans) {
      if (l.getUser().equals(user) && l.getBook().equals(book)) {
        return l;
      }
    }
    return null;
  }
}

public class LibraryService {
  Database db = Database.getInstance();
  DocumentRepository documentRepository = new DefaultDocumentRepository();

  public Response addDocument(
      String authorName,
      String authorEmail,
      String categoryName,
      String documentTitle,
      int documentYear,
      int documentPages) {
    Author author = db.getAuthorOrCreate(authorName, authorEmail);
    Category category = db.getCategoryByNameOrCreate(categoryName, "");
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
    Author author = db.getAuthorOrCreate(authorName, authorEmail);
    Publisher publisher = db.getPublisherOrCreate(publisherName, publisherEmail);
    Category category = db.getCategoryByNameOrCreate(categoryName, "");
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
    Author author = db.getAuthorOrCreate(authorName, authorEmail);
    Category category = db.getCategoryByNameOrCreate(categoryName, "");
    db.documents.offer(
        new Article(
            documentTitle, author, category, journal, volume, number, documentYear, documentPages));
    documentRepository.addDocument(
        new Article(
            documentTitle, author, category, journal, volume, number, documentYear, documentPages));
    return new Response(true, "Article added successfully");
  }

  public List<Document> getDocumentsByAuthor(String authorName) {
    List<Document> documentsByAuthor = new ArrayList<>();
    for (Document document : db.documents) {
      if (document.getAuthor().getName().equals(authorName)) {
        documentsByAuthor.add(document);
      }
    }
    return documentsByAuthor;
  }

  public List<Document> getDocumentsByCategory(String categoryName) {
    List<Document> documentsByCategory = new ArrayList<>();
    for (Document document : db.documents) {
      if (document.getCategory().getName().equals(categoryName)) {
        documentsByCategory.add(document);
      }
    }
    return documentsByCategory;
  }

  public List<Document> getDocumentsByTitle(String title) {
    List<Document> documentsByTitle = new ArrayList<>();
    for (Document document : db.documents) {
      if (document.getTitle().equals(title)) {
        documentsByTitle.add(document);
      }
    }
    return documentsByTitle;
  }

  public List<Document> getAllDocumentsByPopularity() {
    List<Document> sortedDocuments = new ArrayList<>(db.documents);
    sortedDocuments.sort(new DocumentComparator());
    return sortedDocuments;
  }

  public List<Book> getBooks() {
    List<Book> books = new ArrayList<>();
    for (Document document : db.documents) {
      if (document instanceof Book) {
        books.add((Book) document);
      }
    }
    return books;
  }

  public List<Article> getArticles() {
    List<Article> articles = new ArrayList<>();
    for (Document document : db.documents) {
      if (document instanceof Article) {
        articles.add((Article) document);
      }
    }
    return articles;
  }

  public List<Publisher> getPublishers() {
    return db.publishers;
  }

  public List<Author> getAuthors() {
    return db.authors;
  }

  public Response addUser(String name, String email) {
    if (db.getUserByEmail(email) == null) {
      db.users.add(new User(name, email));
      return new Response(true, "User added successfully");
    }
    return new Response(false, "User already exists");
  }

  public Response addLoan(String userEmail, String documentTitle) {
    User user = db.getUserByEmail(userEmail);
    if (user == null) {
      return new Response(false, "User not found");
    }
    Document document = db.getDocumentByTitle(documentTitle);
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
    db.loans.add(new Loan((Book) document, user, "date", "dueDate"));
    return new Response(true, "Loan added");
  }

  public Response completeLoan(String userEmail, String documentTitle) {
    User user = db.getUserByEmail(userEmail);
    if (user == null) {
      return new Response(false, "User not found");
    }
    Document document = db.getDocumentByTitle(documentTitle);
    if (document == null) {
      return new Response(false, "Document not found");
    }
    Loan loan = db.getLoan(user, (Book) document);
    if (loan == null) {
      return new Response(false, "Loan not found");
    } else if (loan.isCompleted()) {
      return new Response(false, "Loan already completed");
    }
    loan.completeLoan();
    return new Response(true, "Loan completed");
  }
}

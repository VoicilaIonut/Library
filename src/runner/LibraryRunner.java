package runner;

import model.*;
import repository.*;
import services.LibraryService;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

class LibraryRunner {
  public static void main(String[] args) {
    LibraryService libraryService =
        new LibraryService(
            new DefaultDocumentRepository(),
            new DefaultAuthorRepository(),
            new DefaultUserRepository(),
            new DefaultPublisherRepository(),
            new DefaultLoanRepository(),
            new DefaultCategoryRepository());
    while (true) displayMenu(libraryService);
  }

  private static void displayMenu(LibraryService libraryService) {
    System.out.println(
        """
                    1. List all documents by popularity
                    2. List documents
                    3. List books
                    4. List articles
                    5. List authors
                    6. List publishers
                    7. List users
                    8. List loans
                    9. List categories
                    10. Add new document
                    11. Add new book
                    12. Add new article
                    13. Search documents by author
                    14. Search documents by category
                    15. Search documents by title
                    16. Add new user
                    17. Loan a book
                    18. Complete a loan
                    19. Exit
                    """);
    Scanner scanner = new Scanner(System.in);
    int option = Integer.parseInt(scanner.nextLine());
    switch (option) {
      case 1 -> listDocumentsByPopularity(libraryService);
      case 2 -> listDocuments(libraryService);
      case 3 -> listBooks(libraryService);
      case 4 -> listArticles(libraryService);
      case 5 -> listAuthors(libraryService);
      case 6 -> listPublishers(libraryService);
      case 7 -> listUsers(libraryService);
      case 8 -> listLoans(libraryService);
      case 9 -> listCategories(libraryService);
      case 10 -> addNewDocumnet(libraryService);
      case 11 -> addNewBook(libraryService);
      case 12 -> AddNewArticle(libraryService);
      case 13 -> searchDocumentsByAuthor(libraryService);
      case 14 -> searchDocumentsByCategory(libraryService);
      case 15 -> searchDocumentsByTitle(libraryService);
      case 16 -> addNewUser(libraryService);
      case 17 -> loanABook(libraryService);
      case 18 -> completeALoan(libraryService);
      case 19 -> exit();
      default -> System.out.println("Invalid option");
    }
  }

  private static void listDocuments(LibraryService libraryService) {
    List<Document> documents = libraryService.getDocuments();
    for (Document document : documents) {
      System.out.println(document);
    }
  }

  private static void addNewDocumnet(LibraryService libraryService) {
    System.out.println("Enter the author name:");
    Scanner scanner = new Scanner(System.in);
    String authorName = scanner.nextLine();
    System.out.println("Enter the author email:");
    String authorEmail = scanner.nextLine();
    System.out.println("Enter the category name:");
    String categoryName = scanner.nextLine();
    System.out.println("Enter the document title:");
    String documentTitle = scanner.nextLine();
    System.out.println("Enter the document year:");
    int documentYear = Integer.parseInt(scanner.nextLine());
    System.out.println("Enter the document pages:");
    int documentPages = Integer.parseInt(scanner.nextLine());
    Response response =
        libraryService.addDocument(
            authorName, authorEmail, categoryName, documentTitle, documentYear, documentPages);
    System.out.println(response.getMessage());
  }

  private static void addNewBook(LibraryService libraryService) {
    System.out.println("Enter the author name:");
    Scanner scanner = new Scanner(System.in);
    String authorName = scanner.nextLine();
    System.out.println("Enter the author email:");
    String authorEmail = scanner.nextLine();
    System.out.println("Enter the publisher name:");
    String publisherName = scanner.nextLine();
    System.out.println("Enter the publisher email:");
    String publisherEmail = scanner.nextLine();
    System.out.println("Enter the category name:");
    String categoryName = scanner.nextLine();
    System.out.println("Enter the document title:");
    String documentTitle = scanner.nextLine();
    System.out.println("Enter the document year:");
    int documentYear = Integer.parseInt(scanner.nextLine());
    System.out.println("Enter the document pages:");
    int documentPages = Integer.parseInt(scanner.nextLine());
    System.out.println("Enter the ISBN:");
    String ISBN = scanner.nextLine();
    System.out.println("Enter the number of copies:");
    int copies = Integer.parseInt(scanner.nextLine());
    Response response =
        libraryService.addBook(
            authorName,
            authorEmail,
            publisherName,
            publisherEmail,
            categoryName,
            documentTitle,
            documentYear,
            documentPages,
            ISBN,
            copies);
    System.out.println(response.getMessage());
  }

  private static void AddNewArticle(LibraryService libraryService) {
    System.out.println("Enter the author name:");
    Scanner scanner = new Scanner(System.in);
    String authorName = scanner.nextLine();
    System.out.println("Enter the author email:");
    String authorEmail = scanner.nextLine();
    System.out.println("Enter the category name:");
    String categoryName = scanner.nextLine();
    System.out.println("Enter the document title:");
    String documentTitle = scanner.nextLine();
    System.out.println("Enter the document year:");
    int documentYear = Integer.parseInt(scanner.nextLine());
    System.out.println("Enter the document pages:");
    int documentPages = Integer.parseInt(scanner.nextLine());
    System.out.println("Enter the journal:");
    String journal = scanner.nextLine();
    System.out.println("Enter the volume:");
    int volume = Integer.parseInt(scanner.nextLine());
    System.out.println("Enter the number:");
    int number = Integer.parseInt(scanner.nextLine());
    Response response =
        libraryService.addArticle(
            authorName,
            authorEmail,
            categoryName,
            documentTitle,
            documentYear,
            documentPages,
            journal,
            volume,
            number);
    System.out.println(response.getMessage());
  }

  private static void searchDocumentsByAuthor(LibraryService libraryService) {
    System.out.println("Enter the author id:");
    Scanner scanner = new Scanner(System.in);
    int authorId = Integer.parseInt(scanner.nextLine());
    List<Document> documents = libraryService.getDocumentsByAuthor(authorId);
    if (documents == null || documents.isEmpty()) {
      System.out.println("No documents found for author with id = " + authorId);
      return;
    }
    for (Document document : documents) {
      System.out.println(document);
    }
  }

  private static void searchDocumentsByCategory(LibraryService libraryService) {
    System.out.println("Enter the category id:");
    Scanner scanner = new Scanner(System.in);
    int categoryId = Integer.parseInt(scanner.nextLine());
    List<Document> documents = libraryService.getDocumentsByCategory(categoryId);
    if (documents == null || documents.isEmpty()) {
      System.out.println("No documents found for category with id = " + categoryId);
      return;
    }
    for (Document document : documents) {
      System.out.println(document);
    }
  }

  private static void searchDocumentsByTitle(LibraryService libraryService) {
    System.out.println("Enter the document title:");
    Scanner scanner = new Scanner(System.in);
    String documentTitle = scanner.nextLine();
    List<Document> documents = libraryService.getAllDocumentsByTitle(documentTitle);
    if (documents == null || documents.isEmpty()) {
      System.out.println("No documents found for title = " + documentTitle);
      return;
    }
    for (Document document : documents) {
      System.out.println(document);
    }
  }

  private static void listDocumentsByPopularity(LibraryService libraryService) {
    PriorityQueue<Document> documents = libraryService.getAllDocumentsByPopularity();
    if (documents == null || documents.isEmpty()) {
      System.out.println("No documents found");
      return;
    }
    for (Document document : documents) {
      System.out.println(document);
    }
  }

  private static void listBooks(LibraryService libraryService) {
    List<Book> books = libraryService.getBooks();
    if (books == null || books.isEmpty()) {
      System.out.println("No books found");
      return;
    }
    for (Book book : books) {
      System.out.println(book);
    }
  }

  private static void addNewUser(LibraryService libraryService) {
    System.out.println("Enter the user name:");
    Scanner scanner = new Scanner(System.in);
    String userName = scanner.nextLine();
    System.out.println("Enter the user email:");
    String userEmail = scanner.nextLine();
    Response response = libraryService.addUser(userName, userEmail);
    System.out.println(response.getMessage());
  }

  private static void loanABook(LibraryService libraryService) {
    System.out.println("Enter the user email:");
    Scanner scanner = new Scanner(System.in);
    String userEmail = scanner.nextLine();
    System.out.println("Enter the book title:");
    String bookTitle = scanner.nextLine();
    Response response = libraryService.addLoan(userEmail, bookTitle);
    System.out.println(response.getMessage());
  }

  private static void completeALoan(LibraryService libraryService) {
    System.out.println("Enter the loan id:");
    Scanner scanner = new Scanner(System.in);
    int loanId = Integer.parseInt(scanner.nextLine());
    Response response = libraryService.completeLoan(loanId);
    System.out.println(response.getMessage());
  }

  private static void listPublishers(LibraryService libraryService) {
    List<Publisher> publishers = libraryService.getPublishers();
    if (publishers == null || publishers.isEmpty()) {
      System.out.println("No publishers found");
      return;
    }
    for (Publisher publisher : publishers) {
      System.out.println(publisher);
    }
  }

  private static void listAuthors(LibraryService libraryService) {
    List<Author> authors = libraryService.getAuthors();
    if (authors == null || authors.isEmpty()) {
      System.out.println("No authors found");
      return;
    }
    for (Author author : authors) {
      System.out.println(author);
    }
  }

  private static void listUsers(LibraryService libraryService) {
    List<User> users = libraryService.getUsers();
    if (users == null || users.isEmpty()) {
      System.out.println("No users found");
      return;
    }
    for (User user : users) {
      System.out.println(user);
    }
  }

  private static void listLoans(LibraryService libraryService) {
    List<Loan> loans = libraryService.getLoans();
    if (loans == null || loans.isEmpty()) {
      System.out.println("No loans found");
      return;
    }
    for (Loan loan : loans) {
      System.out.println(loan);
    }
  }

  private static void listCategories(LibraryService libraryService) {
    List<Category> categories = libraryService.getCategories();
    if (categories == null || categories.isEmpty()) {
      System.out.println("No categories found");
      return;
    }
    for (Category category : categories) {
      System.out.println(category);
    }
  }

  private static void listArticles(LibraryService libraryService) {
    List<Article> articles = libraryService.getArticles();
    if (articles == null || articles.isEmpty()) {
      System.out.println("No articles found");
      return;
    }
    for (Article article : articles) {
      System.out.println(article);
    }
  }

  private static void exit() {
    System.exit(0);
  }
}

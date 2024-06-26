// Lista de tipuri de obiecte:
// 1. Document
// 2. Article
// 3. Book
// 4. Author
// 5. User
// 6. Loan
// 7. Category
// 8. Publisher

// Lista de acțiuni/interogări:
// 1. Adaugă un document nou
// 2. Adaugă o carte nouă
// 3. Adaugă un articol nou
// 4. Cauta documente după autor
// 5. Cauta documente după categorie
// 6. Cauta documente după titlu
// 7. Returneaza lista de documente după popularitate (numărul de împrumuturi)
// 8. Returneaza lista de cărți
// 9. Adaugă un utilizator nou
// 10. Împrumută o carte
// 11. Completează un împrumut
// 12 ? TBD

// Short description of the application:
// The application is a library management system. It allows the user to add new documents, books,
// and articles. The user can search for documents by author, category, or title. The user can also
// see the list of documents sorted by popularity. The user can add new users and loan books to
// them.
// Only the books can be loaned. The books have a number of copies and a number of loans. When a
// book is loaned, the number of copies decreases, and the number of loans increases. When a book is
// returned, the number of copies increases.

import java.util.List;
import java.util.PriorityQueue;
import model.*;
import services.*;

public class Main {
  public static void main(String[] args) {
    //    Just for testing
    LibraryService libraryService = LibraryService.getInstance();
    Response response;
    response = libraryService.addDocument("author1", "email", "category1", "document1", 2020, 1);
    System.out.println(response.getMessage());

    response =
        libraryService.addBook(
            "author1", "email", "publisher1", "email", "category1", "book1", 2020, 1, "12345", 1);
    System.out.println(response.getMessage());

    response =
        libraryService.addArticle(
            "author1", "email", "category1", "article1", 2021, 1, "journal1", 1, 1);
    System.out.println(response.getMessage());
    System.out.println("All documents by popularity:");

    System.out.println("Books:");
    List<Book> books = libraryService.getBooks();
    for (Book book : books) {
      System.out.println(book);
    }
    System.out.println("Articles:");
    List<Article> articles = libraryService.getArticles();
    for (Article article : articles) {
      System.out.println(article);
    }
    List<Document> documentsByAuthor = libraryService.getDocumentsByAuthor(1);
    System.out.println("Documents by author with id =\"1\":");
    for (Document document : documentsByAuthor) {
      System.out.println(document);
    }
    List<Document> documentsByCategory = libraryService.getDocumentsByCategory(1);
    System.out.println("Documents by category with id =\"1\":");
    for (Document document : documentsByCategory) {
      System.out.println(document);
    }
    List<Document> documentsByTitle = libraryService.getAllDocumentsByTitle("article1");
    System.out.println("Documents by title \"article1\":");
    for (Document document : documentsByTitle) {
      System.out.println(document);
    }

    response = libraryService.addUser("name", "email");
    System.out.println(response.getMessage());

    response = libraryService.addLoan("email", "book1");
    System.out.println(response.getMessage());
    int id1 = ((Loan) response.getData()).getId();

    response = libraryService.addLoan("email", "book1");
    System.out.println(response.getMessage());

    response = libraryService.completeLoan(id1);
    System.out.println(response.getMessage());

    response = libraryService.completeLoan(id1);
    System.out.println(response.getMessage());

    libraryService.addBook(
        "author1", "email", "publisher1", "email", "category1", "book2", 2025, 1, "12345", 1);
    System.out.println("All documents by popularity:");
    PriorityQueue<Document> documents = libraryService.getAllDocumentsByPopularity();
    for (Document document : documents) {
      System.out.println(document);
    }

    List<Publisher> publishers = libraryService.getPublishers();
    System.out.println("Publishers:");
    for (Publisher publisher : publishers) {
      System.out.println(publisher);
    }
    List<Author> authors = libraryService.getAuthors();
    System.out.println("Authors:");
    for (Author author : authors) {
      System.out.println(author);
    }

    List<User> users = libraryService.getUsers();
    System.out.println("Users:");
    for (User user : users) {
      System.out.println(user);
    }

    List<Loan> loans = libraryService.getLoans();
    System.out.println("Loans:");
    for (Loan loan : loans) {
      System.out.println(loan);
    }
  }
}

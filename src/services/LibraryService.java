package services;
import model.*;

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
// All the variables in the "add" methods are hardcoded, so the methods will always add the same documents to the database.
// TODO: Refactor the "add" methods to read parameters from the user and add the documents based on the user input.
public class LibraryService {
    Database db = Database.getInstance();

    public void addDocument() {
        String authorName = "author";
        String authorEmail = "email";
        Author author = db.getAuthorOrCreate(authorName, authorEmail);


        String categoryName = "category";
        Category category = db.getCategoryByNameOrCreate(categoryName, "description");

        String documentTitle = "title";
        int documentYear = 2025;
        int documentPages = 100;
        db.documents.offer(new Document(documentTitle, author, category, documentYear, documentPages));
    }

    public void addBook() {
        String authorName = "author";
        String authorEmail = "email";
        Author author = db.getAuthorOrCreate(authorName, authorEmail);

        String publisherName = "publisher";
        String publisherEmail = "email";
        Publisher publisher = db.getPublisherOrCreate(publisherName, publisherEmail);

        String categoryName = "category";
        Category category = db.getCategoryByNameOrCreate(categoryName, "description");

        String documentTitle = "book";
        int documentYear = 2021;
        int documentPages = 100;
        String ISBN = "isbn";
        int copies = 10;
        db.documents.offer(new Book(documentTitle, author, ISBN, category, publisher, documentYear, documentPages, copies));
    }

    public void addArticle() {
        String authorName = "author";
        String authorEmail = "email";
        Author author = db.getAuthorOrCreate(authorName, authorEmail);

        String categoryName = "category";
        Category category = db.getCategoryByNameOrCreate(categoryName, "description");

        String documentTitle = "title";
        int documentYear = 2024;
        int documentPages = 100;
        String journal = "journal";
        int volume = 1;
        int number = 1;
        db.documents.offer(new Article(documentTitle, author, category, journal, volume, number, documentYear, documentPages));
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

    public void addUser() {
        String name = "name";
        String email = "email";
        if (db.getUserByEmail(email) == null) {
            db.users.add(new User(name, email));
        }
    }

    public void addLoan(String userEmail, String documentTitle) {
        User user = db.getUserByEmail(userEmail);
        if (user == null) {
            System.out.println("User not found");
            return;
        }
        Document document = db.getDocumentByTitle(documentTitle);
        if (document == null) {
            System.out.println("Document not found");
            return;
        }

        if (document instanceof Book book) {
            if (book.getCopies() == 0) {
                System.out.println("No copies available");
                return;
            }
        } else {
            System.out.println("Only books can be loaned");
            return;
        }
        db.loans.add(new Loan((Book) document, user, "date", "dueDate"));
        System.out.println("Loan added");
    }

    public void completeLoan(String userEmail, String documentTitle) {
        User user = db.getUserByEmail(userEmail);
        if (user == null) {
            System.out.println("User not found");
            return;
        }
        Document document = db.getDocumentByTitle(documentTitle);
        if (document == null) {
            System.out.println("Document not found");
            return;
        }
        if (document instanceof Book book) {
            if (book.getCopies() == 0) {
                System.out.println("No copies available");
                return;
            }
        } else {
            System.out.println("Only books can be loaned");
            return;
        }
        Loan loan =  db.getLoan(user, (Book) document);
        if (loan == null) {
            System.out.println("Loan not found");
            return;
        }
        loan.completeLoan();
        System.out.println("Loan completed");
    }


}

//1) Definirea sistemului:
//Lista de acțiuni/interogări:
//Adaugă o carte nouă
//Caută o carte după titlu, autor, ISBN
//Împrumută o carte
//Returnează o carte
//Prelungește împrumutul unei cărți
//Afișează lista de cărți disponibile
//Afișează lista de cărți împrumutate
//Afișează lista de utilizatori cu cărți împrumutate
//Generează rapoarte statistice (ex: top 10 cărți împrumutate)
// 2) Definirea obiectelor:
//Lista de tipuri de obiecte:
//Carte
//Autor
//Utilizator
//Exemplar
//Împrumut
//Categorie
//Editură
//Secțiune
//Raport

//2) Definirea obiectelor:
// Lista de tipuri de obiecte:
// Book
// Author
// User
// Loan
// Category

import java.util.ArrayList;
import java.util.List;
import model.*;
import services.*;



public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        libraryService.addDocument();
        libraryService.addBook();
        libraryService.addArticle();
        System.out.println("All documents by popularity:");
        List<Document> documents = libraryService.getAllDocumentsByPopularity();
        for (Document document : documents) {
            System.out.println(document);
        }
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
    }
}
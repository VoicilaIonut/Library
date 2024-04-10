Short description of the application:

The application is a library management system. It allows the user to add new documents, books, and articles. The user can search for documents by author, category, or title. The user can also see the list of documents sorted by popularity. The user can add new users and loan books to them.
Only the books can be loaned. The books have a number of copies and a number of loans. When a book is loaned, the number of copies decreases, and the number of loans increases. When a book is returned, the number of copies increases.


Lista de tipuri de obiecte:
1. Document
2. Article
3. Book
4. Author
5. User
6. Loan
7. Category
8. Publisher

Lista de acțiuni/interogări:
1. Adaugă un document nou
2. Adaugă o carte nouă
3. Adaugă un articol nou
4. Cauta documente după autor
5. Cauta documente după categorie
6. Cauta documente după titlu
7. Returneaza lista de documente după popularitate (numărul de împrumuturi)
8. Returneaza lista de cărți
9. Adaugă un utilizator nou
10. Împrumută o carte
11. Completează un împrumut
12. ? TBD

Cerinte:
1. [x]  clase simple cu atribute private / protected și metode de acces 
    1. Article, Book, Author, User, Loan, Category, Publisher, Document
2. [x]  cel puțin 2 colecții diferite capabile să gestioneze obiectele definiteanterior (eg: List, Set, Map, etc.) dintre care cel puțin una sa fie sortata – se vor folosi array-uri uni- /bidimensionale în cazul în care nu se
parcurg colectiile pana la data checkpoint-ului.
    1. PriorityQueue\<Document>, List\<Autor>, List\<Publisher>, List\<Category>, List\<User>, List\<Loan> in Database from LibraryService
3. [x] utilizare moștenire pentru crearea de clase adiționale și utilizarea lor încadrul colecțiilor 
    1. Document > Article, Book
4. [x] cel puțin o clasă serviciu care sa expună operațiile sistemului LibraryService)
    1. LibraryService
5. [x] o clasa Main din care sunt făcute apeluri către servicii


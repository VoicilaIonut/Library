package model;

import java.util.Comparator;

// Put books first and sort them by number of loans in descending order, then put other documents
// and sort them by year in descending order
public class DocumentComparator implements Comparator<Document> {
  public int compare(Document d1, Document d2) {
    if (d1 instanceof Book && d2 instanceof Book) {
      return Integer.compare(((Book) d2).getNumberOfLoans(), ((Book) d1).getNumberOfLoans());
    } else if (d1 instanceof Book) {
      return -1;
    } else if (d2 instanceof Book) {
      return 1;
    } else {
      return d2.getYear() - d1.getYear();
    }
  }
}

package model;

import java.util.Comparator;

public class DocumentComparator implements Comparator<Document> {
    public int compare(Document d1, Document d2) {
        if (d1 instanceof Book && d2 instanceof Book) {
            return Integer.compare(((Book) d1).getNumberOfLoans(), ((Book) d2).getNumberOfLoans());
        } else {
            return d1.getYear() -  d2.getYear();
        }
    }
}

package repository;

import model.Book;
import model.Loan;
import model.User;

import java.util.List;

public interface LoanRepository {
  void addLoan(Loan loan);

  List<Loan> getLoans();

  Loan getLoan(User user, Book book);
}

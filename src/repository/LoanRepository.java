package repository;

import model.Book;
import model.Loan;
import model.Response;
import model.User;

import java.util.List;

public interface LoanRepository {
  Response addLoan(Loan loan);

  List<Loan> getLoans();

  Loan getLoan(User user, Book book);
}

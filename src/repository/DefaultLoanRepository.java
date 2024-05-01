package repository;

import model.Book;
import model.Loan;
import model.User;

import java.util.List;
import java.util.ArrayList;

public class DefaultLoanRepository implements LoanRepository {
  List<Loan> loans;

  public DefaultLoanRepository() {
    this.loans = new ArrayList<>();
  }

  public List<Loan> getLoans() {
    return this.loans;
  }

  public void addLoan(Loan loan) {
    this.loans.add(loan);
  }

  public Loan getLoan(User user, Book book) {
    for (Loan loan : getLoans()) {
      if (loan.getUser().equals(user) && loan.getBook().equals(book)) {
        return loan;
      }
    }
    return null;
  }
}

package repository;

import java.util.List;
import model.Loan;
import model.Response;

public interface LoanRepository {
  Response addLoan(Loan loan);

  List<Loan> getLoans();

  Loan getLoan(int id);

  Response deleteLoan(int id);

  void completeLoan(Loan loan);
}

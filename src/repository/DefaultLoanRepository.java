package repository;

import model.Book;
import model.Loan;
import model.Response;
import model.User;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DefaultLoanRepository implements LoanRepository {
  //  List<Loan> loans;

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DbConstants.URL);
  }

  public DefaultLoanRepository() {

    //    this.loans = new ArrayList<>();
  }

  public List<Loan> getLoans() {
    String query = "SELECT * FROM Loan";
    List<Loan> loans = new ArrayList<>();

    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        int id = rs.getInt("id");
        int userId = rs.getInt("userId");
        int bookId = rs.getInt("bookId");
        String date = rs.getString("date");
        String dueDate = rs.getString("dueDate");
        String returnDate = rs.getString("returnDate");
        boolean returned = rs.getBoolean("returned");
        boolean extended = rs.getBoolean("extended");
        Loan loan = new Loan(id, userId, bookId, date, dueDate, returnDate, returned, extended);
        loans.add(loan);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }

    return loans;
  }

  public Response addLoan(Loan loan) {
    String query =
        "INSERT INTO Loan (id, userId, bookId, date, dueDate,returnDAte, returned, extended) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, loan.getId());
      stmt.setInt(2, loan.getUserId());
      stmt.setInt(3, loan.getBookId());
      stmt.setString(4, loan.getDate());
      stmt.setString(5, loan.getDueDate());
      stmt.setString(6, loan.getReturnDate());
      stmt.setBoolean(7, loan.isReturned());
      stmt.setBoolean(8, loan.isExtended());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add loan." + e.getMessage());
    }
    return new Response(true, "Loan added successfully");
  }

  public Loan getLoan(User user, Book book) {
    for (Loan loan : getLoans()) {
      if (loan.getUserId() == user.getId() && loan.getBookId() == book.getId()) {
        return loan;
      }
    }
    return null;
  }
}

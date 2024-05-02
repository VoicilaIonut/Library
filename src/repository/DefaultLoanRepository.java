package repository;

import model.Loan;
import model.Response;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DefaultLoanRepository implements LoanRepository {

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DbConstants.URL);
  }

  public DefaultLoanRepository() {}

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
        Loan loan = new Loan(id, userId, bookId, date, dueDate, returnDate, returned);
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
        "INSERT INTO Loan (userId, bookId, date, dueDate, returnDate, returned) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
      stmt.setInt(1, loan.getUserId());
      stmt.setInt(2, loan.getBookId());
      stmt.setString(3, loan.getDate());
      stmt.setString(4, loan.getDueDate());
      stmt.setString(5, loan.getReturnDate());
      stmt.setBoolean(6, loan.isReturned());
      int affectedRows = stmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("Creating loan failed, no rows affected.");
      }

      try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          int id = generatedKeys.getInt(1);
          loan.setId(id);
          return new Response(true, "Loan added successfully.", loan);
        } else {
          throw new SQLException("Creating loan failed, no ID obtained.");
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to add loan. " + e.getMessage());
    }
  }

  public Loan getLoan(int id) {
    String query = "SELECT * FROM Loan WHERE id = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();
      if (rs.next()) {
        int loanId = rs.getInt("id");
        int userId = rs.getInt("userId");
        int bookId = rs.getInt("bookId");
        String date = rs.getString("date");
        String dueDate = rs.getString("dueDate");
        String returnDate = rs.getString("returnDate");
        boolean returned = rs.getBoolean("returned");
        return new Loan(loanId, userId, bookId, date, dueDate, returnDate, returned);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Response deleteLoan(int id) {
    String query = "DELETE FROM Loan WHERE id = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setInt(1, id);
      int affectedRows = stmt.executeUpdate();
      if (affectedRows == 0) {
        throw new SQLException("Deleting loan failed, no rows affected.");
      }
      return new Response(true, "Loan deleted successfully.");
    } catch (SQLException e) {
      e.printStackTrace();
      return new Response(false, "Failed to delete loan. " + e.getMessage());
    }
  }

  public void completeLoan(Loan loan) {
    String query = "UPDATE Loan SET returnDate = ?, returned = ? WHERE id = ?";
    try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
      stmt.setString(1, loan.getReturnDate());
      stmt.setBoolean(2, loan.isReturned());
      stmt.setInt(3, loan.getId());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}

package Model;

import java.time.LocalDate;

public class Emprunt {
    private int loanId;
    private int bookId;
    private int userId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean isExtended;

    // Constructeur
    public Emprunt(int loanId, int bookId, int userId, LocalDate borrowDate, LocalDate dueDate, boolean isExtended) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.isExtended = isExtended;
    }

    // Méthode pour vérifier si l'emprunt est en retard
    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
    }

    // Méthode pour calculer la pénalité
    public long calculatePenalty() {
        if (isOverdue()) {
            return java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now()) * 10;
        }
        return 0;
    }

    // Getters et setters
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isExtended() {
        return isExtended;
    }

    public void setExtended(boolean extended) {
        isExtended = extended;
    }
	@Override
	public String toString() {
	    return "Emprunt {" +
	            "loanId=" + loanId +
	            ", bookId=" + bookId +
	            ", userId=" + userId +
	            ", borrowDate=" + borrowDate +
	            ", dueDate=" + dueDate +
	            ", isExtended=" + isExtended +
	            '}';
	}
	}

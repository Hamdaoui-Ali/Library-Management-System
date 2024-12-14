package Model;

import java.time.LocalDate;

public class Retour {
    private int returnId;
    private int loanId;
    private LocalDate returnDate;
    private long penaltyPaid;

    // Constructeur
    public Retour(int returnId, int loanId, LocalDate returnDate, long penaltyPaid) {
        this.returnId = returnId;
        this.loanId = loanId;
        this.returnDate = returnDate;
        this.penaltyPaid = penaltyPaid;
    }

    // Méthode pour vérifier si le retour était en retard
    public boolean wasLate(LocalDate dueDate) {
        return returnDate.isAfter(dueDate);
    }

    // Getters et setters
    public int getReturnId() {
        return returnId;
    }

    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public long getPenaltyPaid() {
        return penaltyPaid;
    }

    public void setPenaltyPaid(long penaltyPaid) {
        this.penaltyPaid = penaltyPaid;
    }
    @Override
    public String toString() {
        return "Retour {" +
                "returnId=" + returnId +
                ", loanId=" + loanId +
                ", returnDate=" + returnDate +
                ", penaltyPaid=" + penaltyPaid +
                '}';
    }
}

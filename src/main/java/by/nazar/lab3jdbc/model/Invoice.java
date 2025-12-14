package by.nazar.lab3jdbc.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Invoice {
    private int id;
    private int projectId;
    private BigDecimal amount;
    private boolean paid;
    private LocalDate issuedAt;
    private LocalDate paidAt;

    public Invoice() {}

    public Invoice(int id, int projectId, BigDecimal amount, boolean paid, LocalDate issuedAt, LocalDate paidAt) {
        this.id = id;
        this.projectId = projectId;
        this.amount = amount;
        this.paid = paid;
        this.issuedAt = issuedAt;
        this.paidAt = paidAt;
    }

    public Invoice(int projectId, BigDecimal amount, boolean paid, LocalDate issuedAt, LocalDate paidAt) {
        this.projectId = projectId;
        this.amount = amount;
        this.paid = paid;
        this.issuedAt = issuedAt;
        this.paidAt = paidAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public boolean isPaid() { return paid; }
    public void setPaid(boolean paid) { this.paid = paid; }

    public LocalDate getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDate issuedAt) { this.issuedAt = issuedAt; }

    public LocalDate getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDate paidAt) { this.paidAt = paidAt; }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", amount=" + amount +
                ", paid=" + paid +
                ", issuedAt=" + issuedAt +
                ", paidAt=" + paidAt +
                '}';
    }
}

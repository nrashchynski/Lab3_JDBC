package by.nazar.lab3jdbc.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "invoice")
@NamedQueries({
        @NamedQuery(name = "Invoice.findAll",
                query = "SELECT i FROM Invoice i ORDER BY i.issuedAt DESC"),
        @NamedQuery(name = "Invoice.findById",
                query = "SELECT i FROM Invoice i WHERE i.id = :id"),
        @NamedQuery(name = "Invoice.findByProject",
                query = "SELECT i FROM Invoice i WHERE i.project.id = :projectId"),
        @NamedQuery(name = "Invoice.findUnpaid",
                query = "SELECT i FROM Invoice i WHERE i.paid = false ORDER BY i.issuedAt"),
        @NamedQuery(name = "Invoice.findPaid",
                query = "SELECT i FROM Invoice i WHERE i.paid = true ORDER BY i.paidAt DESC"),
        @NamedQuery(name = "Invoice.countAll",
                query = "SELECT COUNT(i) FROM Invoice i")
})
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Integer id;

    // Связь с Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "is_paid")
    private Boolean paid = false;

    @Column(name = "issued_at")
    private LocalDate issuedAt;

    @Column(name = "paid_at")
    private LocalDate paidAt;

    public Invoice() {}

    public Invoice(Project project, BigDecimal amount, LocalDate issuedAt) {
        this.project = project;
        this.amount = amount;
        this.issuedAt = issuedAt;
        this.paid = false;
    }

    public Invoice(Project project, BigDecimal amount) {
        this.project = project;
        this.amount = amount;
        this.issuedAt = LocalDate.now();
        this.paid = false;
    }

    public void markAsPaid() {
        this.paid = true;
        this.paidAt = LocalDate.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Boolean getPaid() { return paid; }
    public void setPaid(Boolean paid) {
        this.paid = paid;
        if (paid && this.paidAt == null) {
            this.paidAt = LocalDate.now();
        }
    }

    public LocalDate getIssuedAt() { return issuedAt; }
    public void setIssuedAt(LocalDate issuedAt) { this.issuedAt = issuedAt; }

    public LocalDate getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDate paidAt) { this.paidAt = paidAt; }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", project=" + (project != null ? project.getName() : "null") +
                ", amount=" + amount +
                ", paid=" + paid +
                ", issuedAt=" + issuedAt +
                ", paidAt=" + paidAt +
                '}';
    }
}

package by.nazar.lab3jdbc.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")

@NamedQueries({
        @NamedQuery(name = "Project.findAll",
                query = "SELECT p FROM Project p ORDER BY p.name"),
        @NamedQuery(name = "Project.findById",
                query = "SELECT p FROM Project p WHERE p.id = :id"),
        @NamedQuery(name = "Project.findByCustomer",
                query = "SELECT p FROM Project p WHERE p.customer.id = :customerId ORDER BY p.name"),
        @NamedQuery(name = "Project.findByStatus",
                query = "SELECT p FROM Project p WHERE p.status = :status ORDER BY p.name"),
        @NamedQuery(name = "Project.countAll",
                query = "SELECT COUNT(p) FROM Project p"),
        // Бизнес-запрос 1: Проекты для заданного заказчика
        @NamedQuery(name = "Project.findAllByCustomerId",
                query = "SELECT p FROM Project p WHERE p.customer.id = :customerId")
})
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    // Связь с Customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Связь с TechSpec
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech_spec_id")
    private TechSpec techSpec;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ProjectStatus status;

    @Column(name = "cost", precision = 15, scale = 2)
    private BigDecimal cost;

    // Обратные связи:
    // 1. У Project много Assignment
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<>();

    // 2. У Project много Invoice
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices = new ArrayList<>();

    // Enum для статуса проекта
    public enum ProjectStatus {
        NEW, IN_PROGRESS, COMPLETED, CANCELLED
    }

    public Project() {}

    public Project(String name, Customer customer, ProjectStatus status, BigDecimal cost) {
        this.name = name;
        this.customer = customer;
        this.status = status;
        this.cost = cost;
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        assignment.setProject(this);
    }

    public void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
        assignment.setProject(null);
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
        invoice.setProject(this);
    }

    public void removeInvoice(Invoice invoice) {
        invoices.remove(invoice);
        invoice.setProject(null);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public TechSpec getTechSpec() { return techSpec; }
    public void setTechSpec(TechSpec techSpec) { this.techSpec = techSpec; }

    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }

    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }

    public List<Assignment> getAssignments() { return assignments; }
    public void setAssignments(List<Assignment> assignments) { this.assignments = assignments; }

    public List<Invoice> getInvoices() { return invoices; }
    public void setInvoices(List<Invoice> invoices) { this.invoices = invoices; }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customer=" + (customer != null ? customer.getName() : "null") +
                ", status=" + status +
                ", cost=" + cost +
                '}';
    }
}
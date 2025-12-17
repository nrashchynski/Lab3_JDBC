package by.nazar.lab3jdbc.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tech_spec")
@NamedQueries({
        @NamedQuery(name = "TechSpec.findAll",
                query = "SELECT t FROM TechSpec t ORDER BY t.title"),
        @NamedQuery(name = "TechSpec.findById",
                query = "SELECT t FROM TechSpec t WHERE t.id = :id"),
        @NamedQuery(name = "TechSpec.findByCustomer",
                query = "SELECT t FROM TechSpec t WHERE t.customer.id = :customerId"),
        @NamedQuery(name = "TechSpec.findByTitle",
                query = "SELECT t FROM TechSpec t WHERE t.title LIKE :title"),
        @NamedQuery(name = "TechSpec.countAll",
                query = "SELECT COUNT(t) FROM TechSpec t")
})
public class TechSpec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tech_spec_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDate createdAt;

    // Обратная связь:
    // У TechSpec много Project
    @OneToMany(mappedBy = "techSpec", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public TechSpec() {}

    public TechSpec(Customer customer, String title, String description, LocalDate createdAt) {
        this.customer = customer;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public TechSpec(Customer customer, String title, String description) {
        this.customer = customer;
        this.title = title;
        this.description = description;
        this.createdAt = LocalDate.now();
    }

    public void addProject(Project project) {
        projects.add(project);
        project.setTechSpec(this);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.setTechSpec(null);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }

    @Override
    public String toString() {
        return "TechSpec{" +
                "id=" + id +
                ", customer=" + (customer != null ? customer.getName() : "null") +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
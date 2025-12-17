package by.nazar.lab3jdbc.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@NamedQueries({
        @NamedQuery(name = "Customer.findAll",
                query = "SELECT c FROM Customer c ORDER BY c.name"),
        @NamedQuery(name = "Customer.findById",
                query = "SELECT c FROM Customer c WHERE c.id = :id"),
        @NamedQuery(name = "Customer.findByName",
                query = "SELECT c FROM Customer c WHERE c.name LIKE :name"),
        @NamedQuery(name = "Customer.countAll",
                query = "SELECT COUNT(c) FROM Customer c")
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "contact_info", length = 255)
    private String contactInfo;

    // Обратные связи:

    // 1. У Customer много TechSpec
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TechSpec> techSpecs = new ArrayList<>();

    // 2. У Customer много Project
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public Customer() {}

    public Customer(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public void addTechSpec(TechSpec techSpec) {
        techSpecs.add(techSpec);
        techSpec.setCustomer(this);
    }

    public void removeTechSpec(TechSpec techSpec) {
        techSpecs.remove(techSpec);
        techSpec.setCustomer(null);
    }

    public void addProject(Project project) {
        projects.add(project);
        project.setCustomer(this);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.setCustomer(null);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    public List<TechSpec> getTechSpecs() { return techSpecs; }
    public void setTechSpecs(List<TechSpec> techSpecs) { this.techSpecs = techSpecs; }

    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactInfo='" + contactInfo + '\'' +
                '}';
    }
}
package by.nazar.lab3jdbc.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "developer")
@NamedQueries({
        @NamedQuery(name = "Developer.findAll",
                query = "SELECT d FROM Developer d ORDER BY d.fullName"),
        @NamedQuery(name = "Developer.findById",
                query = "SELECT d FROM Developer d WHERE d.id = :id"),
        @NamedQuery(name = "Developer.findByQualification",
                query = "SELECT d FROM Developer d WHERE d.qualification = :qualification"),
        @NamedQuery(name = "Developer.findByHourlyRateGreaterThan",
                query = "SELECT d FROM Developer d WHERE d.hourlyRate > :rate"),
        @NamedQuery(name = "Developer.countAll",
                query = "SELECT COUNT(d) FROM Developer d")
})
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "developer_id")
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "qualification", nullable = false, length = 20)
    private String qualification;

    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    // Обратная связь:
    // У Developer много Assignment
    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignments = new ArrayList<>();

    public Developer() {}

    public Developer(String fullName, String qualification, BigDecimal hourlyRate) {
        this.fullName = fullName;
        this.qualification = qualification;
        this.hourlyRate = hourlyRate;
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
        assignment.setDeveloper(this);
    }

    public void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
        assignment.setDeveloper(null);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

    public List<Assignment> getAssignments() { return assignments; }
    public void setAssignments(List<Assignment> assignments) { this.assignments = assignments; }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", qualification='" + qualification + '\'' +
                ", hourlyRate=" + hourlyRate +
                '}';
    }
}
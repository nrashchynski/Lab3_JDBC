package by.nazar.lab3jdbc.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "assignment")
@NamedQueries({
        @NamedQuery(name = "Assignment.findAll",
                query = "SELECT a FROM Assignment a ORDER BY a.assignedAt DESC"),
        @NamedQuery(name = "Assignment.findById",
                query = "SELECT a FROM Assignment a WHERE a.id = :id"),
        @NamedQuery(name = "Assignment.findByProject",
                query = "SELECT a FROM Assignment a WHERE a.project.id = :projectId"),
        @NamedQuery(name = "Assignment.findByDeveloper",
                query = "SELECT a FROM Assignment a WHERE a.developer.id = :developerId"),
        @NamedQuery(name = "Assignment.countAll",
                query = "SELECT COUNT(a) FROM Assignment a"),
        // Бизнес-запрос 3: Разработчики по заданному проекту
        @NamedQuery(name = "Assignment.findDevelopersByProjectId",
                query = "SELECT a.developer FROM Assignment a WHERE a.project.id = :projectId")
})
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Integer id;

    // Связь с Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    // Связь с Developer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer_id", nullable = false)
    private Developer developer;

    @Column(name = "hours_worked")
    private Integer hoursWorked;

    @Column(name = "assigned_at")
    private LocalDate assignedAt;
    public Assignment() {}

    public Assignment(Project project, Developer developer, Integer hoursWorked, LocalDate assignedAt) {
        this.project = project;
        this.developer = developer;
        this.hoursWorked = hoursWorked;
        this.assignedAt = assignedAt;
    }

    public Assignment(Project project, Developer developer, Integer hoursWorked) {
        this.project = project;
        this.developer = developer;
        this.hoursWorked = hoursWorked;
        this.assignedAt = LocalDate.now();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Developer getDeveloper() { return developer; }
    public void setDeveloper(Developer developer) { this.developer = developer; }

    public Integer getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(Integer hoursWorked) { this.hoursWorked = hoursWorked; }

    public LocalDate getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDate assignedAt) { this.assignedAt = assignedAt; }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", project=" + (project != null ? project.getName() : "null") +
                ", developer=" + (developer != null ? developer.getFullName() : "null") +
                ", hoursWorked=" + hoursWorked +
                ", assignedAt=" + assignedAt +
                '}';
    }
}
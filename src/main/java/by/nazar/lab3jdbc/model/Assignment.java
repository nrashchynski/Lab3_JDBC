package by.nazar.lab3jdbc.model;

import java.time.LocalDate;

public class Assignment {
    private int id;
    private int projectId;
    private int developerId;
    private int hoursWorked;
    private LocalDate assignedAt;

    public Assignment() {}

    public Assignment(int id, int projectId, int developerId, int hoursWorked, LocalDate assignedAt) {
        this.id = id;
        this.projectId = projectId;
        this.developerId = developerId;
        this.hoursWorked = hoursWorked;
        this.assignedAt = assignedAt;
    }

    public Assignment(int projectId, int developerId, int hoursWorked, LocalDate assignedAt) {
        this.projectId = projectId;
        this.developerId = developerId;
        this.hoursWorked = hoursWorked;
        this.assignedAt = assignedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public int getDeveloperId() { return developerId; }
    public void setDeveloperId(int developerId) { this.developerId = developerId; }

    public int getHoursWorked() { return hoursWorked; }
    public void setHoursWorked(int hoursWorked) { this.hoursWorked = hoursWorked; }

    public LocalDate getAssignedAt() { return assignedAt; }
    public void setAssignedAt(LocalDate assignedAt) { this.assignedAt = assignedAt; }

    @Override
    public String toString() {
        return "Assignment{" +
                "id=" + id +
                ", projectId=" + projectId +
                ", developerId=" + developerId +
                ", hoursWorked=" + hoursWorked +
                ", assignedAt=" + assignedAt +
                '}';
    }
}

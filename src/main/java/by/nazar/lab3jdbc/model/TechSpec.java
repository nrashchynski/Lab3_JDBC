package by.nazar.lab3jdbc.model;

import java.time.LocalDate;

public class TechSpec {
    private int id;
    private int customerId;
    private String title;
    private String description;
    private LocalDate createdAt;

    public TechSpec() {}

    public TechSpec(int id, int customerId, String title, String description, LocalDate createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public TechSpec(int customerId, String title, String description, LocalDate createdAt) {
        this.customerId = customerId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "TechSpec{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

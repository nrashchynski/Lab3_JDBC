package by.nazar.lab3jdbc.model;

import java.math.BigDecimal;

public class Project {
    private int id;
    private String name;
    private int customerId;
    private Integer techSpecId; // может быть NULL
    private String status;      // ENUM в БД: NEW, IN_PROGRESS, COMPLETED, CANCELLED
    private BigDecimal cost;

    public Project() {}

    public Project(int id, String name, int customerId, Integer techSpecId, String status, BigDecimal cost) {
        this.id = id;
        this.name = name;
        this.customerId = customerId;
        this.techSpecId = techSpecId;
        this.status = status;
        this.cost = cost;
    }

    public Project(String name, int customerId, Integer techSpecId, String status, BigDecimal cost) {
        this.name = name;
        this.customerId = customerId;
        this.techSpecId = techSpecId;
        this.status = status;
        this.cost = cost;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public Integer getTechSpecId() { return techSpecId; }
    public void setTechSpecId(Integer techSpecId) { this.techSpecId = techSpecId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getCost() { return cost; }
    public void setCost(BigDecimal cost) { this.cost = cost; }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", customerId=" + customerId +
                ", techSpecId=" + techSpecId +
                ", status='" + status + '\'' +
                ", cost=" + cost +
                '}';
    }
}

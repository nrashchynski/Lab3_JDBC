package by.nazar.lab3jdbc.model;

import java.math.BigDecimal;

public class Developer {
    private int id;
    private String fullName;
    private String qualification; // ENUM в БД: JUNIOR, MIDDLE, SENIOR, LEAD
    private BigDecimal hourlyRate;

    public Developer() {}

    public Developer(int id, String fullName, String qualification, BigDecimal hourlyRate) {
        this.id = id;
        this.fullName = fullName;
        this.qualification = qualification;
        this.hourlyRate = hourlyRate;
    }

    public Developer(String fullName, String qualification, BigDecimal hourlyRate) {
        this.fullName = fullName;
        this.qualification = qualification;
        this.hourlyRate = hourlyRate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public BigDecimal getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(BigDecimal hourlyRate) { this.hourlyRate = hourlyRate; }

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

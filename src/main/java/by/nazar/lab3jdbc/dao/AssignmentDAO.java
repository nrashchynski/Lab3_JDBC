package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.Assignment;
import by.nazar.lab3jdbc.util.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO {
    public List<Assignment> findAll() {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT assignment_id, project_id, developer_id, hours_worked, assigned_at FROM assignment";
        try (Connection conn = JdbcConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                assignments.add(new Assignment(
                        rs.getInt("assignment_id"),
                        rs.getInt("project_id"),
                        rs.getInt("developer_id"),
                        rs.getInt("hours_worked"),
                        rs.getDate("assigned_at").toLocalDate()
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return assignments;
    }

    public Assignment findById(int id) {
        String sql = "SELECT assignment_id, project_id, developer_id, hours_worked, assigned_at FROM assignment WHERE assignment_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Assignment(
                            rs.getInt("assignment_id"),
                            rs.getInt("project_id"),
                            rs.getInt("developer_id"),
                            rs.getInt("hours_worked"),
                            rs.getDate("assigned_at").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void insert(Assignment a) {
        String sql = "INSERT INTO assignment (project_id, developer_id, hours_worked, assigned_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getProjectId());
            ps.setInt(2, a.getDeveloperId());
            ps.setInt(3, a.getHoursWorked());
            ps.setDate(4, Date.valueOf(a.getAssignedAt()));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void update(Assignment a) {
        String sql = "UPDATE assignment SET project_id=?, developer_id=?, hours_worked=?, assigned_at=? WHERE assignment_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getProjectId());
            ps.setInt(2, a.getDeveloperId());
            ps.setInt(3, a.getHoursWorked());
            ps.setDate(4, Date.valueOf(a.getAssignedAt()));
            ps.setInt(5, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM assignment WHERE assignment_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
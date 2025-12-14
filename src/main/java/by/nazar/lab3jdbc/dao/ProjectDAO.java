package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.Project;
import by.nazar.lab3jdbc.util.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT project_id, name, customer_id, tech_spec_id, status, cost FROM project";
        try (Connection conn = JdbcConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                projects.add(new Project(
                        rs.getInt("project_id"),
                        rs.getString("name"),
                        rs.getInt("customer_id"),
                        rs.getObject("tech_spec_id") != null ? rs.getInt("tech_spec_id") : null,
                        rs.getString("status"),
                        rs.getBigDecimal("cost")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return projects;
    }

    public Project findById(int id) {
        String sql = "SELECT project_id, name, customer_id, tech_spec_id, status, cost FROM project WHERE project_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Project(
                            rs.getInt("project_id"),
                            rs.getString("name"),
                            rs.getInt("customer_id"),
                            rs.getObject("tech_spec_id") != null ? rs.getInt("tech_spec_id") : null,
                            rs.getString("status"),
                            rs.getBigDecimal("cost")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void insert(Project p) {
        String sql = "INSERT INTO project (name, customer_id, tech_spec_id, status, cost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setInt(2, p.getCustomerId());
            if (p.getTechSpecId() != null) {
                ps.setInt(3, p.getTechSpecId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, p.getStatus());
            ps.setBigDecimal(5, p.getCost());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void update(Project p) {
        String sql = "UPDATE project SET name=?, customer_id=?, tech_spec_id=?, status=?, cost=? WHERE project_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setInt(2, p.getCustomerId());
            if (p.getTechSpecId() != null) {
                ps.setInt(3, p.getTechSpecId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setString(4, p.getStatus());
            ps.setBigDecimal(5, p.getCost());
            ps.setInt(6, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM project WHERE project_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
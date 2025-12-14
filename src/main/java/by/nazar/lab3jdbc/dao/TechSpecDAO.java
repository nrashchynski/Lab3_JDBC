package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.TechSpec;
import by.nazar.lab3jdbc.util.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TechSpecDAO {
    public List<TechSpec> findAll() {
        List<TechSpec> specs = new ArrayList<>();
        String sql = "SELECT tech_spec_id, customer_id, title, description, created_at FROM tech_spec";
        try (Connection conn = JdbcConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                specs.add(new TechSpec(
                        rs.getInt("tech_spec_id"),
                        rs.getInt("customer_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("created_at").toLocalDate()
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return specs;
    }

    public TechSpec findById(int id) {
        String sql = "SELECT tech_spec_id, customer_id, title, description, created_at FROM tech_spec WHERE tech_spec_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TechSpec(
                            rs.getInt("tech_spec_id"),
                            rs.getInt("customer_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("created_at").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void insert(TechSpec t) {
        String sql = "INSERT INTO tech_spec (customer_id, title, description, created_at) VALUES (?, ?, ?, ?)";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getCustomerId());
            ps.setString(2, t.getTitle());
            ps.setString(3, t.getDescription());
            ps.setDate(4, Date.valueOf(t.getCreatedAt()));
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void update(TechSpec t) {
        String sql = "UPDATE tech_spec SET customer_id=?, title=?, description=?, created_at=? WHERE tech_spec_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getCustomerId());
            ps.setString(2, t.getTitle());
            ps.setString(3, t.getDescription());
            ps.setDate(4, Date.valueOf(t.getCreatedAt()));
            ps.setInt(5, t.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM tech_spec WHERE tech_spec_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}

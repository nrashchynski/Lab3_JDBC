package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.Developer;
import by.nazar.lab3jdbc.util.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDAO {
    public List<Developer> findAll() {
        List<Developer> developers = new ArrayList<>();
        String sql = "SELECT developer_id, full_name, qualification, hourly_rate FROM developer";
        try (Connection conn = JdbcConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                developers.add(new Developer(
                        rs.getInt("developer_id"),
                        rs.getString("full_name"),
                        rs.getString("qualification"),
                        rs.getBigDecimal("hourly_rate")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return developers;
    }

    public Developer findById(int id) {
        String sql = "SELECT developer_id, full_name, qualification, hourly_rate FROM developer WHERE developer_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Developer(
                            rs.getInt("developer_id"),
                            rs.getString("full_name"),
                            rs.getString("qualification"),
                            rs.getBigDecimal("hourly_rate")
                    );
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void insert(Developer d) {
        String sql = "INSERT INTO developer (full_name, qualification, hourly_rate) VALUES (?, ?, ?)";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getFullName());
            ps.setString(2, d.getQualification());
            ps.setBigDecimal(3, d.getHourlyRate());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void update(Developer d) {
        String sql = "UPDATE developer SET full_name=?, qualification=?, hourly_rate=? WHERE developer_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getFullName());
            ps.setString(2, d.getQualification());
            ps.setBigDecimal(3, d.getHourlyRate());
            ps.setInt(4, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM developer WHERE developer_id=?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}

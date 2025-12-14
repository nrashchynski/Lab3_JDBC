package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.Developer;
import by.nazar.lab3jdbc.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeveloperDAO {
    private static final Logger logger = Logger.getLogger(DeveloperDAO.class.getName());

    public List<Developer> findAll() {
        List<Developer> developers = new ArrayList<>();
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return developers;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT developer_id, full_name, qualification, hourly_rate FROM developer";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                developers.add(new Developer(
                        rs.getInt("developer_id"),
                        rs.getString("full_name"),
                        rs.getString("qualification"),
                        rs.getBigDecimal("hourly_rate")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "findAll failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return developers;
    }

    public Developer findById(int id) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return null;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT developer_id, full_name, qualification, hourly_rate FROM developer WHERE developer_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "findById failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return null;
    }

    public void insert(Developer d) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "INSERT INTO developer (full_name, qualification, hourly_rate) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getFullName());
            ps.setString(2, d.getQualification());
            ps.setBigDecimal(3, d.getHourlyRate());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "insert failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void update(Developer d) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "UPDATE developer SET full_name=?, qualification=?, hourly_rate=? WHERE developer_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getFullName());
            ps.setString(2, d.getQualification());
            ps.setBigDecimal(3, d.getHourlyRate());
            ps.setInt(4, d.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "update failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void delete(int id) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "DELETE FROM developer WHERE developer_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "delete failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }
}

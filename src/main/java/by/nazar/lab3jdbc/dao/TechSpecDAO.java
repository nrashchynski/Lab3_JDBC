package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.TechSpec;
import by.nazar.lab3jdbc.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TechSpecDAO {
    private static final Logger logger = Logger.getLogger(TechSpecDAO.class.getName());

    public List<TechSpec> findAll() {
        List<TechSpec> specs = new ArrayList<>();
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return specs;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT tech_spec_id, customer_id, title, description, created_at FROM tech_spec";
        try (Statement stmt = conn.createStatement();
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "findAll failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return specs;
    }

    public TechSpec findById(int id) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return null;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT tech_spec_id, customer_id, title, description, created_at FROM tech_spec WHERE tech_spec_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "findById failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return null;
    }

    public void insert(TechSpec t) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "INSERT INTO tech_spec (customer_id, title, description, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getCustomerId());
            ps.setString(2, t.getTitle());
            ps.setString(3, t.getDescription());
            ps.setDate(4, Date.valueOf(t.getCreatedAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "insert failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void update(TechSpec t) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "UPDATE tech_spec SET customer_id=?, title=?, description=?, created_at=? WHERE tech_spec_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getCustomerId());
            ps.setString(2, t.getTitle());
            ps.setString(3, t.getDescription());
            ps.setDate(4, Date.valueOf(t.getCreatedAt()));
            ps.setInt(5, t.getId());
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
        String sql = "DELETE FROM tech_spec WHERE tech_spec_id=?";
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

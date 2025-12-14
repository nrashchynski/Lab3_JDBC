package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.Assignment;
import by.nazar.lab3jdbc.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AssignmentDAO {
    private static final Logger logger = Logger.getLogger(AssignmentDAO.class.getName());

    public List<Assignment> findAll() {
        List<Assignment> assignments = new ArrayList<>();
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return assignments;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT assignment_id, project_id, developer_id, hours_worked, assigned_at FROM assignment";
        try (Statement stmt = conn.createStatement();
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "findAll failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return assignments;
    }

    public Assignment findById(int id) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return null;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT assignment_id, project_id, developer_id, hours_worked, assigned_at FROM assignment WHERE assignment_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "findById failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return null;
    }

    public void insert(Assignment a) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "INSERT INTO assignment (project_id, developer_id, hours_worked, assigned_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getProjectId());
            ps.setInt(2, a.getDeveloperId());
            ps.setInt(3, a.getHoursWorked());
            ps.setDate(4, Date.valueOf(a.getAssignedAt()));
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "insert failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void update(Assignment a) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "UPDATE assignment SET project_id=?, developer_id=?, hours_worked=?, assigned_at=? WHERE assignment_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getProjectId());
            ps.setInt(2, a.getDeveloperId());
            ps.setInt(3, a.getHoursWorked());
            ps.setDate(4, Date.valueOf(a.getAssignedAt()));
            ps.setInt(5, a.getId());
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
        String sql = "DELETE FROM assignment WHERE assignment_id=?";
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

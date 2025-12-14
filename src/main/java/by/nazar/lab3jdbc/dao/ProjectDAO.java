package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.Project;
import by.nazar.lab3jdbc.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectDAO {
    private static final Logger logger = Logger.getLogger(ProjectDAO.class.getName());

    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return projects;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT project_id, name, customer_id, tech_spec_id, status, cost FROM project";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Integer techSpecId = rs.getObject("tech_spec_id") != null ? rs.getInt("tech_spec_id") : null;
                projects.add(new Project(
                        rs.getInt("project_id"),
                        rs.getString("name"),
                        rs.getInt("customer_id"),
                        techSpecId,
                        rs.getString("status"),
                        rs.getBigDecimal("cost")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "findAll failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return projects;
    }

    public Project findById(int id) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return null;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT project_id, name, customer_id, tech_spec_id, status, cost FROM project WHERE project_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Integer techSpecId = rs.getObject("tech_spec_id") != null ? rs.getInt("tech_spec_id") : null;
                    return new Project(
                            rs.getInt("project_id"),
                            rs.getString("name"),
                            rs.getInt("customer_id"),
                            techSpecId,
                            rs.getString("status"),
                            rs.getBigDecimal("cost")
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

    public void insert(Project p) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "INSERT INTO project (name, customer_id, tech_spec_id, status, cost) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "insert failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void update(Project p) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "UPDATE project SET name=?, customer_id=?, tech_spec_id=?, status=?, cost=? WHERE project_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
        String sql = "DELETE FROM project WHERE project_id=?";
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

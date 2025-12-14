package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.Invoice;
import by.nazar.lab3jdbc.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceDAO {
    private static final Logger logger = Logger.getLogger(InvoiceDAO.class.getName());

    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return invoices;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT invoice_id, project_id, amount, is_paid, issued_at, paid_at FROM invoice";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Date paidDate = rs.getDate("paid_at");
                invoices.add(new Invoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("project_id"),
                        rs.getBigDecimal("amount"),
                        rs.getBoolean("is_paid"),
                        rs.getDate("issued_at").toLocalDate(),
                        paidDate != null ? paidDate.toLocalDate() : null
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "findAll failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return invoices;
    }

    public Invoice findById(int id) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return null;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT invoice_id, project_id, amount, is_paid, issued_at, paid_at FROM invoice WHERE invoice_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date paidDate = rs.getDate("paid_at");
                    return new Invoice(
                            rs.getInt("invoice_id"),
                            rs.getInt("project_id"),
                            rs.getBigDecimal("amount"),
                            rs.getBoolean("is_paid"),
                            rs.getDate("issued_at").toLocalDate(),
                            paidDate != null ? paidDate.toLocalDate() : null
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

    public void insert(Invoice i) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "INSERT INTO invoice (project_id, amount, is_paid, issued_at, paid_at) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, i.getProjectId());
            ps.setBigDecimal(2, i.getAmount());
            ps.setBoolean(3, i.isPaid());
            ps.setDate(4, Date.valueOf(i.getIssuedAt()));
            if (i.getPaidAt() != null) {
                ps.setDate(5, Date.valueOf(i.getPaidAt()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "insert failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void update(Invoice i) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "UPDATE invoice SET project_id = ?, amount = ?, is_paid = ?, issued_at = ?, paid_at = ? WHERE invoice_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, i.getProjectId());
            ps.setBigDecimal(2, i.getAmount());
            ps.setBoolean(3, i.isPaid());
            ps.setDate(4, Date.valueOf(i.getIssuedAt()));
            if (i.getPaidAt() != null) {
                ps.setDate(5, Date.valueOf(i.getPaidAt()));
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setInt(6, i.getId());
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
        String sql = "DELETE FROM invoice WHERE invoice_id = ?";
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

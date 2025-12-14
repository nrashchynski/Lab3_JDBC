package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.Invoice;
import by.nazar.lab3jdbc.util.JdbcConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT invoice_id, project_id, amount, is_paid, issued_at, paid_at FROM invoice";
        try (Connection conn = JdbcConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                invoices.add(new Invoice(
                        rs.getInt("invoice_id"),
                        rs.getInt("project_id"),
                        rs.getBigDecimal("amount"),
                        rs.getBoolean("is_paid"),
                        rs.getDate("issued_at").toLocalDate(),
                        rs.getDate("paid_at") != null ? rs.getDate("paid_at").toLocalDate() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    public Invoice findById(int id) {
        String sql = "SELECT invoice_id, project_id, amount, is_paid, issued_at, paid_at FROM invoice WHERE invoice_id = ?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Invoice(
                            rs.getInt("invoice_id"),
                            rs.getInt("project_id"),
                            rs.getBigDecimal("amount"),
                            rs.getBoolean("is_paid"),
                            rs.getDate("issued_at").toLocalDate(),
                            rs.getDate("paid_at") != null ? rs.getDate("paid_at").toLocalDate() : null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Invoice i) {
        String sql = "INSERT INTO invoice (project_id, amount, is_paid, issued_at, paid_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
            e.printStackTrace();
        }
    }

    public void update(Invoice i) {
        String sql = "UPDATE invoice SET project_id = ?, amount = ?, is_paid = ?, issued_at = ?, paid_at = ? WHERE invoice_id = ?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM invoice WHERE invoice_id = ?";
        try (Connection conn = JdbcConnector.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

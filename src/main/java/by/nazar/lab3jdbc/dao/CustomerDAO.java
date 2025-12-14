package by.nazar.lab3jdbc.dao;

import by.nazar.lab3jdbc.model.Customer;
import by.nazar.lab3jdbc.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomerDAO {
    private static final Logger logger = Logger.getLogger(CustomerDAO.class.getName());

    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return customers;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT customer_id, name, contact_info FROM customer";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("contact_info")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "findAll failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
        return customers;
    }

    public Customer findById(int id) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return null;
        }

        Connection conn = pool.getConnection();
        String sql = "SELECT customer_id, name, contact_info FROM customer WHERE customer_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("name"),
                            rs.getString("contact_info")
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

    public void insert(Customer c) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "INSERT INTO customer (name, contact_info) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getContactInfo());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "insert failed", e);
        } finally {
            pool.releaseConnection(conn);
        }
    }

    public void update(Customer c) {
        ConnectionPool pool;
        try {
            pool = ConnectionPool.getInstance();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Connection pool init failed", e);
            return;
        }

        Connection conn = pool.getConnection();
        String sql = "UPDATE customer SET name=?, contact_info=? WHERE customer_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getContactInfo());
            ps.setInt(3, c.getId());
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
        String sql = "DELETE FROM customer WHERE customer_id=?";
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

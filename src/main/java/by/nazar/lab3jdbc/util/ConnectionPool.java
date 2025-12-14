package by.nazar.lab3jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionPool {
    private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());
    private static volatile ConnectionPool instance;
    private final BlockingQueue<Connection> pool;
    private static final int POOL_SIZE = 5;

    private ConnectionPool() throws SQLException {
        pool = new ArrayBlockingQueue<>(POOL_SIZE);
        String url = DatabaseProperties.getUrl();
        String user = DatabaseProperties.getUser();
        String password = DatabaseProperties.getPassword();
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.offer(DriverManager.getConnection(url, user, password));
        }
        logger.info("Connection pool initialized with " + POOL_SIZE + " connections.");
    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, "Interrupted while waiting for a connection", e);
            return null;
        }
    }

    public void releaseConnection(Connection conn) {
        if (conn != null) pool.offer(conn);
    }

    public void shutdown() {
        while (!pool.isEmpty()) {
            try {
                pool.poll().close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Error closing connection", e);
            }
        }
        logger.info("Connection pool shut down.");
    }
}

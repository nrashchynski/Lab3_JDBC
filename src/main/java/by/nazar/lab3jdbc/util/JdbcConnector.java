package by.nazar.lab3jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public final class JdbcConnector {
    private static final String PROPS_FILE = "database.properties";
    private static final Properties PROPS = new Properties();
    private static volatile boolean initialized = false;

    private JdbcConnector() {}

    private static synchronized void init() {
        if (initialized) return;

        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(PROPS_FILE)) {

            if (Objects.isNull(is)) {
                throw new IllegalStateException("Файл " + PROPS_FILE + " не найден в resources");
            }

            PROPS.load(is);

            String driver = PROPS.getProperty("db.driver");
            if (driver != null && !driver.isBlank()) {
                Class.forName(driver);
            }

            initialized = true;
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка чтения " + PROPS_FILE, e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBC драйвер не найден: " + PROPS.getProperty("db.driver"), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (!initialized) {
            init();
        }

        String url = PROPS.getProperty("db.url");
        String user = PROPS.getProperty("db.user");
        String pass = PROPS.getProperty("db.password");

        if (url == null || user == null) {
            throw new IllegalStateException("Проверь db.url и db.user в " + PROPS_FILE);
        }

        return DriverManager.getConnection(url, user, pass);
    }
}

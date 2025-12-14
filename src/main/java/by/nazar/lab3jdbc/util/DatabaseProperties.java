package by.nazar.lab3jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseProperties {
    private static final Properties props = new Properties();
    static {
        try (InputStream in = DatabaseProperties.class.getClassLoader().getResourceAsStream("database.properties")) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load database.properties", e);
        }
    }
    public static String getUrl() { return props.getProperty("url"); }
    public static String getUser() { return props.getProperty("user"); }
    public static String getPassword() { return props.getProperty("password"); }
}

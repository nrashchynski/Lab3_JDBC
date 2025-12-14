package by.nazar.lab3jdbc.testDAO;

import by.nazar.lab3jdbc.util.JdbcConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = JdbcConnector.getConnection()) {
            System.out.println("Соединение установлено! Catalog: " + conn.getCatalog());
        } catch (SQLException e) {
            System.err.println("Ошибка SQL: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Ошибка конфигурации: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

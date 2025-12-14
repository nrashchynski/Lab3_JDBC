package by.nazar.lab3jdbc.app;

import by.nazar.lab3jdbc.dao.*;
import by.nazar.lab3jdbc.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import by.nazar.lab3jdbc.util.ConnectionPool;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();
        ProjectDAO projectDAO = new ProjectDAO();
        DeveloperDAO developerDAO = new DeveloperDAO();
        AssignmentDAO assignmentDAO = new AssignmentDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();

        while (true) {
            System.out.println("\n=== Главное меню ===");
            System.out.println("1. Показать всех заказчиков");
            System.out.println("2. Показать все проекты");
            System.out.println("3. Показать всех разработчиков");
            System.out.println("4. Назначить разработчика на проект");
            System.out.println("5. Выставить счёт");
            System.out.println("6. Показать все счета");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> showCustomers(customerDAO);
                case 2 -> showProjects(projectDAO);
                case 3 -> showDevelopers(developerDAO);
                case 4 -> assignDeveloper(assignmentDAO);
                case 5 -> createInvoice(invoiceDAO);
                case 6 -> showInvoices(invoiceDAO);
                case 0 -> {
                    System.out.println("Выход из программы...");
                    exitApp();
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private static void showCustomers(CustomerDAO dao) {
        List<Customer> customers = dao.findAll();
        customers.forEach(System.out::println);
    }

    private static void showProjects(ProjectDAO dao) {
        List<Project> projects = dao.findAll();
        projects.forEach(System.out::println);
    }

    private static void showDevelopers(DeveloperDAO dao) {
        List<Developer> devs = dao.findAll();
        devs.forEach(System.out::println);
    }

    private static void assignDeveloper(AssignmentDAO dao) {
        System.out.print("Введите ID проекта: ");
        int projectId = scanner.nextInt();
        System.out.print("Введите ID разработчика: ");
        int developerId = scanner.nextInt();
        System.out.print("Введите количество часов: ");
        int hours = scanner.nextInt();
        scanner.nextLine();

        Assignment a = new Assignment(projectId, developerId, hours, LocalDate.now());
        dao.insert(a);
        System.out.println("Разработчик назначен!");
    }

    private static void createInvoice(InvoiceDAO dao) {
        System.out.print("Введите ID проекта: ");
        int projectId = scanner.nextInt();
        System.out.print("Введите сумму: ");
        BigDecimal amount = scanner.nextBigDecimal();
        scanner.nextLine();

        Invoice i = new Invoice(projectId, amount, false, LocalDate.now(), null);
        dao.insert(i);
        System.out.println("Счёт выставлен!");
    }

    private static void showInvoices(InvoiceDAO dao) {
        List<Invoice> invoices = dao.findAll();
        invoices.forEach(System.out::println);
    }

    private static void exitApp() {
        try {
            ConnectionPool.getInstance().shutdown();
            System.out.println("Все соединения закрыты. Завершение работы...");
        } catch (Exception e) {
            System.err.println("Ошибка при завершении пула соединений: " + e.getMessage());
        }
        System.exit(0);
    }
}

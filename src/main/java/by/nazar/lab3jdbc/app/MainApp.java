package by.nazar.lab3jdbc.app;

import by.nazar.lab3jdbc.model.Customer;
import by.nazar.lab3jdbc.model.Developer;
import by.nazar.lab3jdbc.model.Project;
import by.nazar.lab3jdbc.model.Assignment;
import by.nazar.lab3jdbc.model.Invoice;
import by.nazar.lab3jdbc.model.TechSpec;

import by.nazar.lab3jdbc.model.*;
import by.nazar.lab3jdbc.repository.*;
import by.nazar.lab3jdbc.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);

    private static final CustomerRepository customerRepo = new CustomerRepository();
    private static final ProjectRepository projectRepo = new ProjectRepository();
    private static final DeveloperRepository developerRepo = new DeveloperRepository();
    private static final AssignmentRepository assignmentRepo = new AssignmentRepository();
    private static final InvoiceRepository invoiceRepo = new InvoiceRepository();

    public static void main(String[] args) {
        System.out.println("СИСТЕМА 'КОМАНДА РАЗРАБОТЧИКОВ' (JPA ВЕРСИЯ)\n");

        while (true) {
            printMainMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1 -> showCustomers();
                case 2 -> showProjects();
                case 3 -> showDevelopers();
                case 4 -> showProjectsByCustomer();
                case 5 -> showDevelopersByProject();
                case 6 -> assignDeveloper();
                case 7 -> createInvoice();
                case 8 -> payInvoice();
                case 9 -> showInvoices();
                case 0 -> {
                    exitApp();
                    return;
                }
                default -> System.out.println("Неверный выбор!");
            }

        }
    }

    private static void printMainMenu() {
        System.out.println("\nГЛАВНОЕ МЕНЮ (JPA)");
        System.out.println("1. Показать всех заказчиков");
        System.out.println("2. Показать все проекты");
        System.out.println("3. Показать всех разработчиков");
        System.out.println("4. Показать проекты заказчика (бизнес-запрос 1)");
        System.out.println("5. Показать разработчиков проекта (бизнес-запрос 3)");
        System.out.println("6. Назначить разработчика на проект (бизнес-запрос 4)");
        System.out.println("7. Выставить счёт");
        System.out.println("8. Оплатить счёт (бизнес-запрос 5)");
        System.out.println("9. Показать все счета");
        System.out.println("0. Выход");
    }

    private static void showCustomers() {
        System.out.println("\nВсе заказчики: ");
        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            System.out.println("Нет заказчиков.");
        } else {
            customers.forEach(c -> System.out.println("ID: " + c.getId() +
                    ", Имя: " + c.getName() +
                    ", Контакт: " + c.getContactInfo()));
        }
    }

    private static void showProjects() {
        System.out.println("\nВсе проекты: ");
        List<Project> projects = projectRepo.findAll();
        if (projects.isEmpty()) {
            System.out.println("Нет проектов.");
        } else {
            projects.forEach(p -> System.out.println("ID: " + p.getId() +
                    ", Название: " + p.getName() +
                    ", Заказчик: " + p.getCustomer().getName() +
                    ", Статус: " + p.getStatus() +
                    ", Стоимость: " + p.getCost()));
        }
    }

    private static void showDevelopers() {
        System.out.println("\nВсе разработчики: ");
        List<Developer> developers = developerRepo.findAllDevelopers();
        if (developers.isEmpty()) {
            System.out.println("Нет разработчиков.");
        } else {
            developers.forEach(d -> System.out.println("ID: " + d.getId() +
                    ", Имя: " + d.getFullName() +
                    ", Квалификация: " + d.getQualification() +
                    ", Ставка: " + d.getHourlyRate() + " руб/час"));
        }
    }

    private static void showProjectsByCustomer() {
        System.out.println("\nПроекты заказчика: ");

        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            System.out.println("Нет заказчиков.");
            return;
        }

        System.out.println("Выберите заказчика:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i).getName());
        }

        int choice = getIntInput("Номер заказчика: ") - 1;
        if (choice < 0 || choice >= customers.size()) {
            System.out.println("Неверный выбор.");
            return;
        }

        Customer customer = customers.get(choice);
        List<Project> projects = projectRepo.findAllByCustomer(customer);

        System.out.println("\nПроекты заказчика '" + customer.getName() + "':");
        if (projects.isEmpty()) {
            System.out.println("Нет проектов.");
        } else {
            projects.forEach(p -> System.out.println("  - " + p.getName() +
                    " [" + p.getStatus() + ", " + p.getCost() + "]"));
        }
    }

    private static void showDevelopersByProject() {
        System.out.println("\n: Разработчики проекта: ");

        List<Project> projects = projectRepo.findAll();
        if (projects.isEmpty()) {
            System.out.println("Нет проектов.");
            return;
        }

        System.out.println("Выберите проект:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getName());
        }

        int choice = getIntInput("Номер проекта: ") - 1;
        if (choice < 0 || choice >= projects.size()) {
            System.out.println("Неверный выбор.");
            return;
        }

        Project project = projects.get(choice);
        List<Developer> developers = assignmentRepo.findDevelopersByProject(project);

        System.out.println("\nРазработчики на проекте '" + project.getName() + "':");
        if (developers.isEmpty()) {
            System.out.println("Нет разработчиков.");
        } else {
            developers.forEach(d -> System.out.println("  - " + d.getFullName() +
                    " (" + d.getQualification() + ")"));
        }
    }

    private static void assignDeveloper() {
        System.out.println("\nНазначение разработчика на проект: ");

        // Выбор проекта
        List<Project> projects = projectRepo.findAll();
        if (projects.isEmpty()) {
            System.out.println("Нет проектов.");
            return;
        }

        System.out.println("Выберите проект:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getName());
        }

        int projectChoice = getIntInput("Номер проекта: ") - 1;
        if (projectChoice < 0 || projectChoice >= projects.size()) {
            System.out.println("Неверный выбор проекта.");
            return;
        }

        // Выбор разработчика
        List<Developer> developers = developerRepo.findAllDevelopers();
        if (developers.isEmpty()) {
            System.out.println("Нет разработчиков.");
            return;
        }

        System.out.println("\nВыберите разработчика:");
        for (int i = 0; i < developers.size(); i++) {
            System.out.println((i + 1) + ". " + developers.get(i).getFullName());
        }

        int devChoice = getIntInput("Номер разработчика: ") - 1;
        if (devChoice < 0 || devChoice >= developers.size()) {
            System.out.println("Неверный выбор разработчика.");
            return;
        }

        // Ввод часов
        int hours = getIntInput("Количество часов: ");
        if (hours <= 0) {
            System.out.println("Часы должны быть > 0.");
            return;
        }

        try {
            Project project = projects.get(projectChoice);
            Developer developer = developers.get(devChoice);

            Assignment assignment = assignmentRepo.assignDeveloperToProject(project, developer, hours);

            System.out.println("\nНазначение создано!");
            System.out.println("   Проект: " + project.getName());
            System.out.println("   Разработчик: " + developer.getFullName());
            System.out.println("   Часы: " + assignment.getHoursWorked());

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void createInvoice() {
        System.out.println("\nВыставление счёта: ");

        List<Project> projects = projectRepo.findAll();
        if (projects.isEmpty()) {
            System.out.println("Нет проектов.");
            return;
        }

        System.out.println("Выберите проект:");
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getName());
        }

        int choice = getIntInput("Номер проекта: ") - 1;
        if (choice < 0 || choice >= projects.size()) {
            System.out.println("Неверный выбор.");
            return;
        }

        Project project = projects.get(choice);
        BigDecimal amount = getBigDecimalInput("Сумма счёта: ");

        try {
            Invoice invoice = invoiceRepo.createInvoice(project, amount);
            System.out.println("\nСчёт #" + invoice.getId() + " выставлен!");
            System.out.println("   Проект: " + project.getName());
            System.out.println("   Сумма: " + invoice.getAmount());
            System.out.println("   Дата: " + invoice.getIssuedAt());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void payInvoice() {
        System.out.println("\nОплата счёта");

        List<Invoice> unpaid = invoiceRepo.findUnpaidInvoices();
        if (unpaid.isEmpty()) {
            System.out.println("Нет неоплаченных счетов.");
            return;
        }

        System.out.println("Неоплаченные счета:");
        for (int i = 0; i < unpaid.size(); i++) {
            Invoice inv = unpaid.get(i);
            System.out.println((i + 1) + ". Счёт #" + inv.getId() +
                    " - Проект: " + inv.getProject().getName() +
                    " - Сумма: " + inv.getAmount());
        }

        int choice = getIntInput("Номер счёта для оплаты: ") - 1;
        if (choice < 0 || choice >= unpaid.size()) {
            System.out.println("Неверный выбор.");
            return;
        }

        try {
            Invoice invoice = unpaid.get(choice);
            invoiceRepo.payInvoice(invoice.getId());

            System.out.println("\nСчёт #" + invoice.getId() + " оплачен!");
            System.out.println("   Дата оплаты: " + invoice.getPaidAt());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void showInvoices() {
        System.out.println("\nВсе счета");
        List<Invoice> invoices = invoiceRepo.findAll();
        if (invoices.isEmpty()) {
            System.out.println("Нет счетов.");
        } else {
            invoices.forEach(inv -> {
                String status = inv.getPaid() ? "ОПЛАЧЕН" : "НЕ ОПЛАЧЕН";
                System.out.println("Счёт #" + inv.getId() +
                        " - Проект: " + inv.getProject().getName() +
                        " - Сумма: " + inv.getAmount() +
                        " - Статус: " + status +
                        " - Выдан: " + inv.getIssuedAt());
            });
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    private static BigDecimal getBigDecimalInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return new BigDecimal(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число (например: 1000.50).");
            }
        }
    }

    private static void exitApp() {
        System.out.println("\nЗакрытие соединений JPA...");
        JPAUtil.close();
        System.out.println("Выход из программы.");
        scanner.close();
    }
}
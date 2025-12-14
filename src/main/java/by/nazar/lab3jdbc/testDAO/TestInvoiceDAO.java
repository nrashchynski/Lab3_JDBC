package by.nazar.lab3jdbc.testDAO;

import by.nazar.lab3jdbc.dao.InvoiceDAO;
import by.nazar.lab3jdbc.model.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestInvoiceDAO {
    public static void main(String[] args) {
        InvoiceDAO dao = new InvoiceDAO();

        System.out.println("=== Все счета ===");
        List<Invoice> invoices = dao.findAll();
        invoices.forEach(System.out::println);

        System.out.println("\n=== Добавление счёта ===");
        dao.insert(new Invoice(1, BigDecimal.valueOf(10000), false, LocalDate.now(), null));

        System.out.println("\n=== Поиск счёта с ID=1 ===");
        System.out.println(dao.findById(1));

        System.out.println("\n=== Обновление счёта с ID=1 ===");
        Invoice i = dao.findById(1);
        if (i != null) {
            i.setPaid(true);
            i.setPaidAt(LocalDate.now());
            dao.update(i);
            System.out.println("Обновлено: " + i);
        }

        System.out.println("\n=== Удаление счёта с ID=2 ===");
        dao.delete(2);
    }
}

package by.nazar.lab3jdbc.testDAO;

import by.nazar.lab3jdbc.dao.TechSpecDAO;
import by.nazar.lab3jdbc.model.TechSpec;

import java.time.LocalDate;
import java.util.List;

public class TestTechSpecDAO {
    public static void main(String[] args) {
        TechSpecDAO dao = new TechSpecDAO();

        System.out.println("=== Все ТЗ ===");
        List<TechSpec> specs = dao.findAll();
        specs.forEach(System.out::println);

        System.out.println("\n=== Добавление ТЗ ===");
        dao.insert(new TechSpec(1, "CRM система", "Описание CRM", LocalDate.now()));

        System.out.println("\n=== Поиск ТЗ с ID=1 ===");
        System.out.println(dao.findById(1));

        System.out.println("\n=== Обновление ТЗ с ID=1 ===");
        TechSpec t = dao.findById(1);
        if (t != null) {
            t.setDescription("Обновлённое описание CRM");
            dao.update(t);
            System.out.println("Обновлено: " + t);
        }

        System.out.println("\n=== Удаление ТЗ с ID=2 ===");
        dao.delete(2);
    }
}

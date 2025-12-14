package by.nazar.lab3jdbc.testDAO;

import by.nazar.lab3jdbc.dao.DeveloperDAO;
import by.nazar.lab3jdbc.model.Developer;

import java.math.BigDecimal;
import java.util.List;

public class TestDeveloperDAO {
    public static void main(String[] args) {
        DeveloperDAO dao = new DeveloperDAO();

        System.out.println("=== Все разработчики ===");
        List<Developer> devs = dao.findAll();
        devs.forEach(System.out::println);

        System.out.println("\n=== Добавление разработчика ===");
        dao.insert(new Developer("Иван Иванов", "JUNIOR", BigDecimal.valueOf(20)));

        System.out.println("\n=== Поиск разработчика с ID=1 ===");
        System.out.println(dao.findById(1));

        System.out.println("\n=== Обновление разработчика с ID=1 ===");
        Developer d = dao.findById(1);
        if (d != null) {
            d.setQualification("MIDDLE");
            dao.update(d);
            System.out.println("Обновлено: " + d);
        }

        System.out.println("\n=== Удаление разработчика с ID=2 ===");
        dao.delete(2);
    }
}

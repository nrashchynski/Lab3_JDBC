package by.nazar.lab3jdbc.testDAO;

import by.nazar.lab3jdbc.dao.AssignmentDAO;
import by.nazar.lab3jdbc.model.Assignment;

import java.time.LocalDate;
import java.util.List;

public class TestAssignmentDAO {
    public static void main(String[] args) {
        AssignmentDAO dao = new AssignmentDAO();

        System.out.println("=== Все назначения ===");
        List<Assignment> assignments = dao.findAll();
        assignments.forEach(System.out::println);

        System.out.println("\n=== Добавление назначения ===");
        dao.insert(new Assignment(1, 1, 40, LocalDate.now()));

        System.out.println("\n=== Поиск назначения с ID=1 ===");
        System.out.println(dao.findById(1));

        System.out.println("\n=== Обновление назначения с ID=1 ===");
        Assignment a = dao.findById(1);
        if (a != null) {
            a.setHoursWorked(50);
            dao.update(a);
            System.out.println("Обновлено: " + a);
        }

        System.out.println("\n=== Удаление назначения с ID=2 ===");
        dao.delete(2);
    }
}

package by.nazar.lab3jdbc.testDAO;

import by.nazar.lab3jdbc.dao.ProjectDAO;
import by.nazar.lab3jdbc.model.Project;

import java.math.BigDecimal;
import java.util.List;

public class TestProjectDAO {
    public static void main(String[] args) {
        ProjectDAO dao = new ProjectDAO();

        System.out.println("=== Все проекты ===");
        List<Project> projects = dao.findAll();
        projects.forEach(System.out::println);

        System.out.println("\n=== Добавление проекта ===");
        dao.insert(new Project("ERP система", 1, null, "NEW", BigDecimal.valueOf(50000)));

        System.out.println("\n=== Поиск проекта с ID=1 ===");
        System.out.println(dao.findById(1));

        System.out.println("\n=== Обновление проекта с ID=1 ===");
        Project p = dao.findById(1);
        if (p != null) {
            p.setStatus("IN_PROGRESS");
            dao.update(p);
            System.out.println("Обновлено: " + p);
        }

        System.out.println("\n=== Удаление проекта с ID=2 ===");
        dao.delete(2);
    }
}

package by.nazar.lab3jdbc.testDAO;

import by.nazar.lab3jdbc.dao.CustomerDAO;
import by.nazar.lab3jdbc.model.Customer;
import java.util.List;

public class TestCustomerDAO {
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();

        System.out.println("=== Все заказчики ===");
        List<Customer> customers = dao.findAll();
        customers.forEach(System.out::println);

        System.out.println("\n=== Добавление заказчика ===");
        dao.insert(new Customer("ООО Гамма", "gamma@example.com"));

        System.out.println("\n=== Поиск заказчика с ID=1 ===");
        System.out.println(dao.findById(1));

        System.out.println("\n=== Обновление заказчика с ID=1 ===");
        Customer c = dao.findById(1);
        if (c != null) {
            c.setContactInfo("updated_alpha@example.com");
            dao.update(c);
            System.out.println("Обновлён: " + c);
        }

        System.out.println("\n=== Удаление заказчика с ID=2 ===");
        dao.delete(2);
    }
}

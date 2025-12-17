package by.nazar.lab3jdbc.repository;

import by.nazar.lab3jdbc.model.Customer;
import by.nazar.lab3jdbc.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {

    public Customer save(Customer customer) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (customer.getId() == null) {
                em.persist(customer);
            } else {
                customer = em.merge(customer);
            }
            em.getTransaction().commit();
            return customer;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error saving customer: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<Customer> findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Customer customer = em.find(Customer.class, id);
            return Optional.ofNullable(customer);
        } finally {
            em.close();
        }
    }

    public List<Customer> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Customer customer = em.find(Customer.class, id);
            if (customer != null) {
                em.remove(customer);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting customer: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<Customer> findByName(String name) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Customer> query = em.createNamedQuery("Customer.findByName", Customer.class);
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createNamedQuery("Customer.countAll", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
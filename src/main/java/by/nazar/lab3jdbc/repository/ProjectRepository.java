package by.nazar.lab3jdbc.repository;

import by.nazar.lab3jdbc.model.Customer;
import by.nazar.lab3jdbc.model.Project;
import by.nazar.lab3jdbc.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProjectRepository {

    public Project save(Project project) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (project.getId() == null) {
                em.persist(project);
            } else {
                project = em.merge(project);
            }
            em.getTransaction().commit();
            return project;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error saving project: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<Project> findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Project project = em.find(Project.class, id);
            return Optional.ofNullable(project);
        } finally {
            em.close();
        }
    }

    public List<Project> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Project> query = em.createNamedQuery("Project.findAll", Project.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * БИЗНЕС-ЗАПРОС 1: Вывести информацию обо всех проектах для заданного заказчика.
     */
    public List<Project> findAllByCustomer(Customer customer) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Project> query = em.createNamedQuery("Project.findAllByCustomerId", Project.class);
            query.setParameter("customerId", customer.getId());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Project> findAllByCustomerId(Integer customerId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Project> query = em.createNamedQuery("Project.findAllByCustomerId", Project.class);
            query.setParameter("customerId", customerId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Project> findByStatus(Project.ProjectStatus status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Project> query = em.createNamedQuery("Project.findByStatus", Project.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Project project = em.find(Project.class, id);
            if (project != null) {
                em.remove(project);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting project: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public BigDecimal calculateProjectCost(Integer projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT SUM(a.hoursWorked * d.hourlyRate) " +
                    "FROM Assignment a " +
                    "JOIN a.developer d " +
                    "WHERE a.project.id = :projectId";

            TypedQuery<BigDecimal> query = em.createQuery(jpql, BigDecimal.class);
            query.setParameter("projectId", projectId);
            BigDecimal result = query.getSingleResult();
            return result != null ? result : BigDecimal.ZERO;
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createNamedQuery("Project.countAll", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
package by.nazar.lab3jdbc.repository;

import by.nazar.lab3jdbc.model.Developer;
import by.nazar.lab3jdbc.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class DeveloperRepository {

    public Developer save(Developer developer) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (developer.getId() == null) {
                em.persist(developer);
            } else {
                developer = em.merge(developer);
            }
            em.getTransaction().commit();
            return developer;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error saving developer: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<Developer> findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Developer developer = em.find(Developer.class, id);
            return Optional.ofNullable(developer);
        } finally {
            em.close();
        }
    }

    /**
     * БИЗНЕС-ЗАПРОС 2: Вывести информацию обо всех разработчиках команды.
     */
    public List<Developer> findAllDevelopers() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Developer> query = em.createNamedQuery("Developer.findAll", Developer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Developer developer = em.find(Developer.class, id);
            if (developer != null) {
                em.remove(developer);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting developer: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<Developer> findByQualification(String qualification) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Developer> query = em.createNamedQuery("Developer.findByQualification", Developer.class);
            query.setParameter("qualification", qualification);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Developer> findByHourlyRateGreaterThan(BigDecimal rate) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Developer> query = em.createNamedQuery("Developer.findByHourlyRateGreaterThan", Developer.class);
            query.setParameter("rate", rate);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createNamedQuery("Developer.countAll", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
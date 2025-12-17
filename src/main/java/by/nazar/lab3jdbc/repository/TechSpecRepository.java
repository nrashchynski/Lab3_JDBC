package by.nazar.lab3jdbc.repository;

import by.nazar.lab3jdbc.model.Customer;
import by.nazar.lab3jdbc.model.TechSpec;
import by.nazar.lab3jdbc.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class TechSpecRepository {

    public TechSpec save(TechSpec techSpec) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (techSpec.getId() == null) {
                em.persist(techSpec);
            } else {
                techSpec = em.merge(techSpec);
            }
            em.getTransaction().commit();
            return techSpec;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error saving tech spec: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<TechSpec> findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TechSpec techSpec = em.find(TechSpec.class, id);
            return Optional.ofNullable(techSpec);
        } finally {
            em.close();
        }
    }

    public List<TechSpec> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<TechSpec> query = em.createNamedQuery("TechSpec.findAll", TechSpec.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<TechSpec> findByCustomer(Customer customer) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<TechSpec> query = em.createNamedQuery("TechSpec.findByCustomer", TechSpec.class);
            query.setParameter("customerId", customer.getId());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<TechSpec> findByTitle(String title) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<TechSpec> query = em.createNamedQuery("TechSpec.findByTitle", TechSpec.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            TechSpec techSpec = em.find(TechSpec.class, id);
            if (techSpec != null) {
                em.remove(techSpec);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting tech spec: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createNamedQuery("TechSpec.countAll", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
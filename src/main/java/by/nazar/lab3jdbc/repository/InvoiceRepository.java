package by.nazar.lab3jdbc.repository;

import by.nazar.lab3jdbc.model.Invoice;
import by.nazar.lab3jdbc.model.Project;
import by.nazar.lab3jdbc.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class InvoiceRepository {

    public Invoice save(Invoice invoice) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (invoice.getId() == null) {
                em.persist(invoice);
            } else {
                invoice = em.merge(invoice);
            }
            em.getTransaction().commit();
            return invoice;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error saving invoice: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<Invoice> findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Invoice invoice = em.find(Invoice.class, id);
            return Optional.ofNullable(invoice);
        } finally {
            em.close();
        }
    }

    public List<Invoice> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Invoice> query = em.createNamedQuery("Invoice.findAll", Invoice.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * БИЗНЕС-ЗАПРОС 5: Оплатить счёт.
     */
    public Invoice payInvoice(Integer invoiceId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Invoice invoice = em.find(Invoice.class, invoiceId);
            if (invoice == null) {
                throw new RuntimeException("Invoice with id " + invoiceId + " not found");
            }

            if (invoice.getPaid()) {
                throw new RuntimeException("Invoice already paid");
            }

            invoice.markAsPaid();
            invoice = em.merge(invoice);

            em.getTransaction().commit();
            return invoice;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error paying invoice: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<Invoice> findByProject(Project project) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Invoice> query = em.createNamedQuery("Invoice.findByProject", Invoice.class);
            query.setParameter("projectId", project.getId());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Invoice> findUnpaidInvoices() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Invoice> query = em.createNamedQuery("Invoice.findUnpaid", Invoice.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Invoice> findPaidInvoices() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Invoice> query = em.createNamedQuery("Invoice.findPaid", Invoice.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Invoice createInvoice(Project project, BigDecimal amount) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Invoice invoice = new Invoice(project, amount, LocalDate.now());
            em.persist(invoice);

            project.addInvoice(invoice);

            em.getTransaction().commit();
            return invoice;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error creating invoice: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Invoice invoice = em.find(Invoice.class, id);
            if (invoice != null) {
                em.remove(invoice);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting invoice: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createNamedQuery("Invoice.countAll", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
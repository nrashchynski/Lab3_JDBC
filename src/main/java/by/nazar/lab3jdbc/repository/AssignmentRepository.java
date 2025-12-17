package by.nazar.lab3jdbc.repository;

import by.nazar.lab3jdbc.model.Assignment;
import by.nazar.lab3jdbc.model.Developer;
import by.nazar.lab3jdbc.model.Project;
import by.nazar.lab3jdbc.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AssignmentRepository {

    public Assignment save(Assignment assignment) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (assignment.getId() == null) {
                em.persist(assignment);
            } else {
                assignment = em.merge(assignment);
            }
            em.getTransaction().commit();
            return assignment;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error saving assignment: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    /**
     * БИЗНЕС-ЗАПРОС 4: Назначить разработчика на проект.
     */
    public Assignment assignDeveloperToProject(Project project, Developer developer, Integer hours) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Проверяем, нет ли уже такого назначения
            TypedQuery<Assignment> query = em.createQuery(
                    "SELECT a FROM Assignment a WHERE a.project.id = :projectId AND a.developer.id = :developerId",
                    Assignment.class
            );
            query.setParameter("projectId", project.getId());
            query.setParameter("developerId", developer.getId());

            List<Assignment> existing = query.getResultList();
            Assignment assignment;

            if (existing.isEmpty()) {
                // Создаем новое назначение
                assignment = new Assignment(project, developer, hours, LocalDate.now());
                em.persist(assignment);

                // Обновляем связи
                project.addAssignment(assignment);
                developer.addAssignment(assignment);
            } else {
                // Обновляем существующее
                assignment = existing.get(0);
                assignment.setHoursWorked(hours);
                assignment.setAssignedAt(LocalDate.now());
                assignment = em.merge(assignment);
            }

            em.getTransaction().commit();
            return assignment;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error assigning developer to project: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<Assignment> findById(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Assignment assignment = em.find(Assignment.class, id);
            return Optional.ofNullable(assignment);
        } finally {
            em.close();
        }
    }

    public List<Assignment> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Assignment> query = em.createNamedQuery("Assignment.findAll", Assignment.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * БИЗНЕС-ЗАПРОС 3: Вывести информацию о разработчиках по заданному проекту.
     */
    public List<Developer> findDevelopersByProject(Project project) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Developer> query = em.createNamedQuery("Assignment.findDevelopersByProjectId", Developer.class);
            query.setParameter("projectId", project.getId());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Developer> findDevelopersByProjectId(Integer projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Developer> query = em.createNamedQuery("Assignment.findDevelopersByProjectId", Developer.class);
            query.setParameter("projectId", projectId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Assignment> findByProject(Project project) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Assignment> query = em.createNamedQuery("Assignment.findByProject", Assignment.class);
            query.setParameter("projectId", project.getId());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Assignment> findByDeveloper(Developer developer) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Assignment> query = em.createNamedQuery("Assignment.findByDeveloper", Assignment.class);
            query.setParameter("developerId", developer.getId());
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Integer id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Assignment assignment = em.find(Assignment.class, id);
            if (assignment != null) {
                em.remove(assignment);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error deleting assignment: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public long count() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createNamedQuery("Assignment.countAll", Long.class);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
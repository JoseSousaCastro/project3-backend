package aor.paj.proj3_vc_re_jc.dao;

import aor.paj.proj3_vc_re_jc.entity.RetroEventEntity;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class RetroEventDAO extends RetroAbstractDAO<RetroEventEntity> {

    private static final long serialVersionUID = 1L;

    public RetroEventDAO() {
        super(RetroEventEntity.class);
    }

    private EntityManager getEntityManager() {
        return em;
    }

    public List<RetroEventEntity> findEventsByUserId(int userId) {
        List<RetroEventEntity> events = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            events = em.createQuery("SELECT e FROM RetroEventEntity e JOIN e.members m WHERE m.user.userId = :userId", RetroEventEntity.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<RetroEventEntity> findEventsByCategory(int category) {
        List<RetroEventEntity> events = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            events = em.createQuery("SELECT e FROM RetroEventEntity e WHERE e.category = :category", RetroEventEntity.class)
                    .setParameter("category", category)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<RetroEventEntity> findEventsByUserIdAndCategory(int userId, int category) {
        List<RetroEventEntity> events = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            events = em.createQuery("SELECT e FROM RetroEventEntity e JOIN e.members m WHERE m.user.userId = :userId AND e.category = :category", RetroEventEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("category", category)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<RetroEventEntity> findEventsByUserRole(int userId, int role) {
        List<RetroEventEntity> events = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            events = em.createQuery("SELECT e FROM RetroEventEntity e JOIN e.members m WHERE m.user.userId = :userId AND m.role = :role", RetroEventEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("role", role)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<RetroEventEntity> findEventsByUserRoleAndCategory(int userId, int role, int category) {
        List<RetroEventEntity> events = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            events = em.createQuery("SELECT e FROM RetroEventEntity e JOIN e.members m WHERE m.user.userId = :userId AND m.role = :role AND e.category = :category", RetroEventEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("role", role)
                    .setParameter("category", category)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public List<RetroEventEntity> findEventsByUserIdAndDeleted(int userId, boolean deleted) {
        List<RetroEventEntity> events = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            events = em.createQuery("SELECT e FROM RetroEventEntity e JOIN e.members m WHERE m.user.userId = :userId AND e.deleted = :deleted", RetroEventEntity.class)
                    .setParameter("userId", userId)
                    .setParameter("deleted", deleted)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
}
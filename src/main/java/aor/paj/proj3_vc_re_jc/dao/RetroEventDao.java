package aor.paj.proj3_vc_re_jc.dao;

import aor.paj.proj3_vc_re_jc.entity.RetroEventEntity;
import aor.paj.proj3_vc_re_jc.entity.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;

import java.util.List;

@Stateless
public class RetroEventDao extends AbstractDao<RetroEventEntity> {

    private static final long serialVersionUID = 1L;

    public RetroEventDao() {
        super(RetroEventEntity.class);
    }

    private EntityManager getEntityManager() {
        return em;
    }

    public RetroEventEntity findEventByTitle(String title) {
        List<RetroEventEntity> events = em.createQuery("SELECT e FROM RetroEventEntity e WHERE e.title = :title", RetroEventEntity.class)
                .setParameter("title", title)
                .getResultList();
        return events.isEmpty() ? null : events.get(0);
    }

    public void saveRetroEvent(RetroEventEntity retroEvent) {
        try {
            em.persist(retroEvent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving retro event", e);
        }
    }

    public void addMemberToRetroEvent(int eventId, UserEntity member) {
        try {
            RetroEventEntity retroEvent = em.find(RetroEventEntity.class, eventId);
            if (retroEvent != null) {
                retroEvent.addMember(member);
                em.merge(retroEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding member to retro event", e);
        }
    }

    public List<RetroEventEntity> getAllRetroEvents() {
        return em.createQuery("SELECT e FROM RetroEventEntity e", RetroEventEntity.class).getResultList();
    }
}
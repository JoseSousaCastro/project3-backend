package aor.paj.proj3_vc_re_jc.dao;

import aor.paj.proj3_vc_re_jc.entity.RetroCommentEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RetroCommentDAO {

    private final EntityManager em;

    public RetroCommentDAO(EntityManager em) {
        this.em = em;
    }

    public void saveRetroComment(RetroCommentEntity retroComment) {
        em.getTransaction().begin();
        em.persist(retroComment);
        em.getTransaction().commit();
    }

    public RetroCommentEntity getRetroCommentById(int commentId) {
        return em.find(RetroCommentEntity.class, commentId);
    }

    public List<RetroCommentEntity> getAllRetroComments() {
        return em.createQuery("SELECT c FROM RetroCommentEntity c", RetroCommentEntity.class).getResultList();
    }

    public void updateRetroComment(RetroCommentEntity retroComment) {
        em.getTransaction().begin();
        em.merge(retroComment);
        em.getTransaction().commit();
    }

    public void deleteRetroComment(int commentId) {
        RetroCommentEntity retroComment = getRetroCommentById(commentId);
        if (retroComment != null) {
            em.getTransaction().begin();
            em.remove(retroComment);
            em.getTransaction().commit();
        }
    }
}
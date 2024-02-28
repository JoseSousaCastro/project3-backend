package aor.paj.proj3_vc_re_jc.dao;

import aor.paj.proj3_vc_re_jc.entity.RetroCommentEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;

import java.util.List;

@Stateless
public class RetroCommentDao extends AbstractDao<RetroCommentEntity> {

    private static final long serialVersionUID = 1L;

    public RetroCommentDao() {
        super(RetroCommentEntity.class);
    }

    public void saveRetroComment(RetroCommentEntity retroComment) {
        try {
            em.persist(retroComment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving retro comment", e);
        }
    }

    public void updateRetroComment(RetroCommentEntity retroComment) {
        try {
        em.merge(retroComment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating retro comment", e);
        }
    }

    public void deleteRetroComment(int commentId) {
        try {
            RetroCommentEntity retroComment = getRetroCommentById(commentId);
            if (retroComment != null) {
                em.remove(retroComment);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting retro comment", e);
        }
    }

    public RetroCommentEntity getRetroCommentById(int commentId) {
        return em.find(RetroCommentEntity.class, commentId);
    }

    public List<RetroCommentEntity> getAllRetroComments() {
        return em.createQuery("SELECT c FROM RetroCommentEntity c", RetroCommentEntity.class).getResultList();
    }
}
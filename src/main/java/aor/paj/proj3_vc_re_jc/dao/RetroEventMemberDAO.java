package aor.paj.proj3_vc_re_jc.dao;

import aor.paj.proj3_vc_re_jc.entity.RetroEventMemberEntity;
import jakarta.persistence.EntityManager;

public class RetroEventMemberDAO {

    private final EntityManager em;

    public RetroEventMemberDAO(EntityManager em) {
        this.em = em;
    }

    public void saveRetroEventMember(RetroEventMemberEntity retroEventMember) {
        em.getTransaction().begin();
        em.persist(retroEventMember);
        em.getTransaction().commit();
    }
}

package aor.paj.proj3_vc_re_jc.dao;

import aor.paj.proj3_vc_re_jc.entity.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.Collections;
import java.util.List;

@Stateless
public class UserDao extends AbstractDao<UserEntity> {

    private static final long serialVersionUID = 1L;

    public UserDao() {
        super(UserEntity.class);
    }

    public UserEntity findUserByToken(String token) {
        try {
            return (UserEntity) em.createNamedQuery("User.findUserByToken").setParameter("token", token)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public UserEntity findUserByUsername(String username) {
        try {
            return (UserEntity) em.createNamedQuery("User.findUserByUsername").setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public List<UserEntity> allUsers() {
        try {
            return em.createNamedQuery("User.findAllUsers", UserEntity.class).getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    public UserEntity findUserById(int id) {
        try {
            return (UserEntity) em.createNamedQuery("User.findUserById")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}





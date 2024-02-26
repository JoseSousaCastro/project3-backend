package aor.paj.proj3_vc_re_jc.dao;

import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;

import java.io.Serializable;
import java.util.List;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class RetroAbstractDAO<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Class<T> entityClass;

    @PersistenceContext(unitName = "retro")
    protected EntityManager em;

    public RetroAbstractDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> findAll() {
        final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return em.createQuery(criteriaQuery).getResultList();
    }

    public void deleteAll() {
        final CriteriaDelete<T> criteriaDelete = em.getCriteriaBuilder().createCriteriaDelete(entityClass);
        criteriaDelete.from(entityClass);
        em.createQuery(criteriaDelete).executeUpdate();
    }
}
package aor.paj.proj3_vc_re_jc.dao;

import aor.paj.proj3_vc_re_jc.entity.CategoryEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

@Stateless
public class CategoryDao extends AbstractDao<CategoryEntity> {

    private static final long serialVersionUID = 11L;

    public CategoryDao() {
        super(CategoryEntity.class);
    }

    public CategoryEntity findCategoryById(int id) {
        try {
            return (CategoryEntity) em.createNamedQuery("Category.findCategoryById").setParameter("id", id)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public CategoryEntity findCategoryByName(String name) {
        try {
            return (CategoryEntity) em.createNamedQuery("Category.findCategoryByName").setParameter("name", name)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
}
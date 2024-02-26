package aor.paj.proj3_vc_re_jc.bean;


import aor.paj.proj3_vc_re_jc.dao.CategoryDao;
import aor.paj.proj3_vc_re_jc.dao.TaskDao;
import aor.paj.proj3_vc_re_jc.dto.CategoryDto;
import aor.paj.proj3_vc_re_jc.entity.CategoryEntity;
import aor.paj.proj3_vc_re_jc.entity.TaskEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.io.Serializable;
import java.util.ArrayList;


@Stateless
public class CategoryBean implements Serializable {
    @EJB
    CategoryDao categoryDao;
    @EJB
    TaskDao taskDao;

    public CategoryBean() {
    }

    public boolean addCategory(CategoryDto ctg) {
        CategoryEntity c = categoryDao.findCategoryById(ctg.getId());
        if(c== null){
            categoryDao.persist(convertCategoryFromDtoToEntity(ctg));
            return true;
        }
        return false;
    }

    public boolean removeCategory(CategoryDto ctg) {
        CategoryEntity c = categoryDao.findCategoryById(ctg.getId());
        if(c != null){
            ArrayList<TaskEntity> tasks = taskDao.getTasksByCategoryId(ctg.getId());
            if(tasks == null) {
                categoryDao.remove(c);
                return true;
            }
            else return false;
        }
        return false;
    }

    public boolean updateCategoryName(CategoryDto ctg) {
        CategoryEntity c = categoryDao.findCategoryById(ctg.getId());
        if (c != null) {
            c.setCategoryName(ctg.getName());
            return true;
        }
        return false;
    }

    private CategoryEntity convertCategoryFromDtoToEntity(CategoryDto c){
        CategoryEntity ctgEntity = new CategoryEntity();
        ctgEntity.setId(c.getId());
        ctgEntity.setCategoryName(c.getName());
        return ctgEntity;
    }

    private CategoryDto convertCategoryFromEntityToDto(CategoryEntity c){
        CategoryDto ctgDto = new CategoryDto();
        ctgDto.setId(c.getId());
        ctgDto.setName(c.getCategoryName());
        return ctgDto;
    }

}


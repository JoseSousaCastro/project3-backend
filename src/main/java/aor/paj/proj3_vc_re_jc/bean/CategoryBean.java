package aor.paj.proj3_vc_re_jc.bean;


import aor.paj.proj3_vc_re_jc.dao.CategoryDao;
import aor.paj.proj3_vc_re_jc.dao.TaskDao;
import aor.paj.proj3_vc_re_jc.dto.CategoryDto;
import aor.paj.proj3_vc_re_jc.dto.RoleDto;
import aor.paj.proj3_vc_re_jc.entity.CategoryEntity;
import aor.paj.proj3_vc_re_jc.entity.TaskEntity;
import aor.paj.proj3_vc_re_jc.enums.UserRole;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.core.Response;

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

    public Response addCategory(CategoryDto ctg, RoleDto user) {
        // Convert integer role to UserRole enum
        UserRole userRole = UserRole.valueOf(user.getRole());
        // Check if the user is a PRODUCT_OWNER
        if (userRole == UserRole.PRODUCT_OWNER) {
            CategoryEntity c = categoryDao.findCategoryById(ctg.getId());
            if (c == null) {
                categoryDao.persist(convertCategoryFromDtoToEntity(ctg));
                return Response.status(201).entity("Category created successfully").build();
            } else {
                return Response.status(404).entity("Impossible to create category").build();
            }
        } else {
            return Response.status(403).entity("Invalid role permissions").build();
        }
    }

    public Response removeCategory(CategoryDto ctg, RoleDto user) {
        // Convert integer role to UserRole enum
        UserRole userRole = UserRole.valueOf(user.getRole());
        // Check if the user is a PRODUCT_OWNER
        if (userRole == UserRole.PRODUCT_OWNER) {
            CategoryEntity c = categoryDao.findCategoryById(ctg.getId());
            if (c != null) {
                ArrayList<TaskEntity> tasks = taskDao.getTasksByCategoryId(ctg.getId());
                if (tasks == null || tasks.isEmpty()) {
                    categoryDao.remove(c);
                    return Response.status(200).entity("Category removed successfully").build();
                } else {
                    return Response.status(400).entity("Category cannot be removed as there are tasks associated with it").build();
                }
            } else {
                return Response.status(404).entity("Category with this name is not found").build();
            }
        } else {
            return Response.status(403).entity("Invalid role permissions").build();
        }
    }

    public Response updateCategoryName(CategoryDto ctg, RoleDto user) {
        // Convert integer role to UserRole enum
        UserRole userRole = UserRole.valueOf(user.getRole());
        // Check if the user is a PRODUCT_OWNER
        if (userRole == UserRole.PRODUCT_OWNER) {
            CategoryEntity c = categoryDao.findCategoryById(ctg.getId());
            if (c != null) {
                c.setCategoryName(ctg.getName());
                return Response.status(200).entity("Category name updated successfully").build();
            } else {
                return Response.status(404).entity("Category not found").build();
            }
        } else {
            return Response.status(403).entity("Invalid role permissions").build();
        }
    }

    private CategoryEntity convertCategoryFromDtoToEntity(CategoryDto c) {
        CategoryEntity ctgEntity = new CategoryEntity();
        ctgEntity.setId(c.getId());
        ctgEntity.setCategoryName(c.getName());
        return ctgEntity;
    }

    private CategoryDto convertCategoryFromEntityToDto(CategoryEntity c) {
        CategoryDto ctgDto = new CategoryDto();
        ctgDto.setId(c.getId());
        ctgDto.setName(c.getCategoryName());
        return ctgDto;
    }

}


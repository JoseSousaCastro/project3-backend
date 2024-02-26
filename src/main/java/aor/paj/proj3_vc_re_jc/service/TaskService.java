package aor.paj.proj3_vc_re_jc.service;


import aor.paj.proj3_vc_re_jc.bean.CategoryBean;
import aor.paj.proj3_vc_re_jc.bean.TaskBean;
import aor.paj.proj3_vc_re_jc.bean.UserBean;
import aor.paj.proj3_vc_re_jc.dao.CategoryDao;
import aor.paj.proj3_vc_re_jc.dto.*;
import aor.paj.proj3_vc_re_jc.entity.CategoryEntity;
import aor.paj.proj3_vc_re_jc.enums.TaskPriority;
import aor.paj.proj3_vc_re_jc.enums.TaskState;
import aor.paj.proj3_vc_re_jc.enums.UserRole;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/tasks")
public class TaskService {

    @Inject
    UserBean userBean;
    @Inject
    TaskBean taskBean;
    @Inject
    CategoryBean ctgBean;
    @EJB
    CategoryDao categoryDao;

    @Context
    HttpServletRequest request;


    // Return Task by Id
    @GET
    @Path("/task")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsersTasks(@HeaderParam("token") String token, @HeaderParam("taskId") int taskId) {
        if (userBean.tokenExist(token)) {
            TaskDto task = taskBean.getTask(taskId);
            if (task != null) {
                return Response.status(200).entity(task).build();
            } else {
                return Response.status(404).entity("Task with this id not found").build();
            }
        } else {
            return Response.status(401).entity("Invalid Token").build();
        }
    }


    // Return all Tasks from user
    @GET
    @Path("/userTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUserTasks(@HeaderParam("token") String token, LoginDto user) {
        if (userBean.tokenExist(token)) {
            ArrayList<TaskDto> tasks = taskBean.getUserTasks(user);
            if (tasks != null) {
                return Response.status(200).entity(tasks).build();
            } else {
                return Response.status(404).entity("No tasks from this user found").build();
            }
        } else {
            return Response.status(401).entity("Invalid Token").build();
        }
    }


    // Return all deleted Tasks
    @GET
    @Path("/deletedTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDeletedTasks(@HeaderParam("token") String token) {
        if (userBean.tokenExist(token)) {
            ArrayList<TaskDto> tasks = taskBean.getDeletedTasks();
            if (tasks != null) {
                return Response.status(200).entity(tasks).build();
            } else {
                return Response.status(404).entity("No deleted tasks found").build();
            }
        } else {
            return Response.status(401).entity("Invalid Token").build();
        }
    }


    // Return all Tasks with same Category
    @GET
    @Path("/categoryTasks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCategoryTasks(@HeaderParam("token") String token, CategoryDto category) {
        if (userBean.tokenExist(token)) {
            ArrayList<TaskDto> tasks = taskBean.getCategoryTasks(category);
            if (tasks != null) {
                return Response.status(200).entity(tasks).build();
            } else {
                return Response.status(404).entity("Tasks with this category name not found").build();
            }
        } else {
            return Response.status(401).entity("Invalid Token").build();
        }
    }


    // Add Task
    @POST
    @Path("/addTask")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTask(@HeaderParam("token") String token, TaskDto task) {
        if (userBean.tokenExist(token)) {
            if (task.getTitle() == null || task.getTitle().isEmpty()) {
                return Response.status(400).entity("Task title cannot be empty").build();
            }
            if (task.getDescription() == null || task.getDescription().isEmpty()) {
                return Response.status(400).entity("Task description cannot be empty").build();
            }
            // Set default priority if not provided
            if (task.getPriority() == null) {
                task.setPriority(TaskPriority.LOW_PRIORITY);
            } else {
                // Validate if the provided priority is within the possible values
                try {
                    TaskPriority.valueOf(task.getPriority().name());
                } catch (IllegalArgumentException e) {
                    return Response.status(400).entity("Invalid priority input value").build();
                }
            }
            // Perform date validation
            if (task.getStartDate() == null || task.getEndDate() == null) {
                return Response.status(400).entity("Both start date and end date must be provided").build();
            }
            if (task.getEndDate().isBefore(task.getStartDate())) {
                return Response.status(400).entity("End date must be after start date").build();
            }
            // Check if category exists
            CategoryEntity category = categoryDao.findCategoryByName(task.getCategory());
            if (category == null) {
                return Response.status(400).entity("Category does not exist").build();
            }
            boolean added = taskBean.addTask(token, task);
            if (added) {
                return Response.status(201).entity("Task created successfully").build();
            } else {
                return Response.status(404).entity("Impossible to create task. Verify all fields").build();
            }
        } else {
            return Response.status(401).entity("Invalid Token").build();
        }
    }


    // Update Task (Edit the contents of the task)
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(@HeaderParam("token") String token, @HeaderParam("taskId") int taskId, TaskDto task, RoleDto user) {
        if (userBean.tokenExist(token)) {
            return Response.status(401).entity("Invalid token").build();
        }
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            return Response.status(400).entity("Task title cannot be empty").build();
        }
        if (task.getDescription() == null || task.getDescription().isEmpty()) {
            return Response.status(400).entity("Task description cannot be empty").build();
        }
        // Set default priority if not provided
        if (task.getPriority() == null) {
            task.setPriority(TaskPriority.LOW_PRIORITY);
        } else {
            // Validate if the provided priority is within the possible values
            try {
                TaskPriority.valueOf(task.getPriority().name());
            } catch (IllegalArgumentException e) {
                return Response.status(400).entity("Invalid priority input value").build();
            }
        }
        // Perform date validation
        if (task.getStartDate() == null || task.getEndDate() == null) {
            return Response.status(400).entity("Both start date and end date must be provided").build();
        }
        if (task.getEndDate().isBefore(task.getStartDate())) {
            return Response.status(400).entity("End date must be after start date").build();
        }
        // Check if category exists
        CategoryEntity category = categoryDao.findCategoryByName(task.getCategory());
        if (category == null) {
            return Response.status(400).entity("Category does not exist").build();
        }
        // Convert integer role to UserRole enum
        UserRole userRole = UserRole.valueOf(user.getRole());
        boolean updated;
        // Check if the user is a SCRUM_MASTER or PRODUCT_OWNER
        if (userRole == UserRole.DEVELOPER) {
            updated = taskBean.updateOwnTask(taskId, task, user.getUsername());
        } else {
            updated = taskBean.updateTask(taskId, task);
        }
        if (updated) {
            return Response.status(200).entity("Task updated successfully").build();
        } else {
            return Response.status(404).entity("Impossible to edit task. Verify all fields").build();
        }
    }


    // Update Task Status (Move task between columns)
    @POST
    @Path("/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTaskStatus(@HeaderParam("token") String token, @HeaderParam("taskId") int taskId,
                                     UpdateTaskStateDto newStatus) {
        if (userBean.tokenExist(token)) {
            // Validate if the provided task state is within the possible values
            if (newStatus == null || newStatus.getState() == null) {
                return Response.status(400).entity("Invalid task state input value").build();
            }
            try {
                TaskState.valueOf(newStatus.getState().name());
            } catch (IllegalArgumentException e) {
                return Response.status(400).entity("Invalid task state input value").build();
            }
            boolean updated = taskBean.updateTaskStatus(taskId, newStatus.getState());
            if (updated) {
                return Response.status(200).entity("Task status updated successfully").build();
            } else {
                return Response.status(404).entity("Impossible to update task status. Task not found or invalid status").build();
            }
        } else {
            return Response.status(401).entity("Invalid Token").build();
        }
    }


    // Remove Task (Recycle bin)
    @POST
    @Path("/updateDeleted")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDeleted(@HeaderParam("token") String token, @HeaderParam("taskId") int taskId) {
        if (userBean.tokenExist(token)) {
            boolean removed = taskBean.removeTask(taskId);
            if (removed) {
                return Response.status(200).entity("Task moved to recycle bin successfully").build();
            } else {
                return Response.status(404).entity("Task with this id not found").build();
            }
        } else {
            return Response.status(401).entity("Invalid token").build();
        }
    }


    // Restore Task from Recycle bin
    @POST
    @Path("/restoreDeleted")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRestoreTask(@HeaderParam("token") String token, @HeaderParam("taskId") int taskId) {
        if (userBean.tokenExist(token)) {
            boolean removed = taskBean.restoreDeletedTask(taskId);
            if (removed) {
                return Response.status(200).entity("Task restored successfully").build();
            } else {
                return Response.status(404).entity("Task with this id not found").build();
            }
        } else {
            return Response.status(401).entity("Invalid token").build();
        }
    }


    // Remove Task Permanently
    @DELETE
    @Path("/remove")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeTask(@HeaderParam("token") String token, @HeaderParam("taskId") int taskId) {
        if (userBean.tokenExist(token)) {
            boolean removed = taskBean.removeTaskPermanently(taskId);
            if (removed) {
                return Response.status(200).entity("Task removed successfully").build();
            } else {
                return Response.status(404).entity("Task with this id not found").build();
            }
        } else {
            return Response.status(401).entity("Invalid token").build();
        }
    }


    // Remove all Tasks from user (Recycle bin)
    @POST
    @Path("/updateDeleted/userTasks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAllUserTasks(@HeaderParam("token") String token, RoleDto user) {
        if (userBean.tokenExist(token)) {
            // Convert integer role to UserRole enum
            UserRole userRole = UserRole.valueOf(user.getRole());
            // Check if the user is a PRODUCT_OWNER
            if (userRole == UserRole.PRODUCT_OWNER) {
                boolean removed = taskBean.removeAllUserTasks(user);
                if (removed) {
                    return Response.status(200).entity("Tasks moved to recycle bin successfully").build();
                } else {
                    return Response.status(404).entity("No user found or no tasks from this user found").build();
                }
            } else {
                return Response.status(403).entity("Unauthorized: Only PRODUCT_OWNER can perform this action").build();
            }
        } else {
            return Response.status(401).entity("Invalid token").build();
        }
    }


    // Add Task Category
    @POST
    @Path("/category/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategory(@HeaderParam("token") String token, CategoryDto category) {
        if (userBean.tokenExist(token)) {
            if (category == null || category.getName() == null || category.getName().isEmpty()) {
                return Response.status(400).entity("Category name cannot be empty").build();
            }
            boolean added = ctgBean.addCategory(category);
            if (added) {
                return Response.status(201).entity("Category created successfully").build();
            } else {
                return Response.status(404).entity("Impossible to create category").build();
            }
        } else {
            return Response.status(401).entity("Invalid Token").build();
        }
    }


    // Remove Task Category
    @DELETE
    @Path("/category/remove")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeCategory(@HeaderParam("token") String token, CategoryDto category) {
        if (userBean.tokenExist(token)) {
            boolean removed = ctgBean.removeCategory(category);
            if (removed) {
                return Response.status(200).entity("Category removed successfully").build();
            } else {
                return Response.status(404).entity("Category with this name is not found or there are tasks with this category").build();
            }
        } else {
            return Response.status(401).entity("Invalid token").build();
        }
    }


    // Update Task Category
    @POST
    @Path("/category/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCategory(@HeaderParam("token") String token, CategoryDto category) {
        if (userBean.tokenExist(token)) {
            if (category == null || category.getName() == null || category.getName().isEmpty()) {
                return Response.status(400).entity("Category name cannot be empty").build();
            }
            boolean updated = ctgBean.updateCategoryName(category);
            if (updated) {
                return Response.status(200).entity("Category name updated successfully").build();
            } else {
                return Response.status(404).entity("Impossible to update category name").build();
            }
        } else {
            return Response.status(401).entity("Invalid Token").build();
        }
    }
}

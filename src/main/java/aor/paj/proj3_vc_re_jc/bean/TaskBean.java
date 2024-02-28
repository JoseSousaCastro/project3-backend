package aor.paj.proj3_vc_re_jc.bean;

import aor.paj.proj3_vc_re_jc.dao.CategoryDao;
import aor.paj.proj3_vc_re_jc.dao.TaskDao;
import aor.paj.proj3_vc_re_jc.dao.UserDao;
import aor.paj.proj3_vc_re_jc.dto.CategoryDto;
import aor.paj.proj3_vc_re_jc.dto.LoginDto;
import aor.paj.proj3_vc_re_jc.dto.RoleDto;
import aor.paj.proj3_vc_re_jc.dto.TaskDto;
import aor.paj.proj3_vc_re_jc.entity.CategoryEntity;
import aor.paj.proj3_vc_re_jc.entity.TaskEntity;
import aor.paj.proj3_vc_re_jc.entity.UserEntity;
import aor.paj.proj3_vc_re_jc.enums.TaskState;
import aor.paj.proj3_vc_re_jc.enums.UserRole;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;
import java.util.ArrayList;

@Stateless
public class TaskBean implements Serializable {
    @EJB
    TaskDao taskDao;
    @EJB
    UserDao userDao;
    @EJB
    CategoryDao categoryDao;

    public TaskBean() {
    }

    public boolean addTask(String token, TaskDto t) {
        UserEntity userEntity = userDao.findUserByToken(token);
        if (userEntity != null) {
            TaskEntity taskEntity = convertTaskFromDtoToEntity(t);
            taskEntity.setCreator(userEntity);
            taskEntity.setState(TaskState.TODO);
            taskEntity.setDeleted(false);
            taskDao.persist(taskEntity);
            return true;
        }
        return false;
    }

    public Response removeTask(int id, RoleDto user) {
        // Convert integer role to UserRole enum
        UserRole userRole = user.getRole();
        // Check if the user is a SCRUM_MASTER or PRODUCT_OWNER
        if (userRole == UserRole.SCRUM_MASTER || userRole == UserRole.PRODUCT_OWNER) {
            TaskEntity t = taskDao.findTaskById(id);
            if (t != null) {
                t.setDeleted(true);
                return Response.status(200).entity("Task removed successfully").build();
            } else {
                return Response.status(404).entity("Task not found").build();
            }
        } else {
            return Response.status(401).entity("Unauthorized: Only SCRUM_MASTER or PRODUCT_OWNER can access user tasks").build(); // Unauthorized access
        }
    }

    public Response removeAllUserTasks(LoginDto user, RoleDto roleDto) {
        // Convert integer role to UserRole enum
        UserRole userRole = roleDto.getRole();
        // Check if the user is a PRODUCT_OWNER
        if (userRole == UserRole.PRODUCT_OWNER) {
            UserEntity userEntity = userDao.findUserByUsername(user.getUsername());
            if (userEntity != null) {
                ArrayList<TaskEntity> tasks = taskDao.findTasksByUser(userEntity);
                if (tasks != null) {
                    for (TaskEntity t : tasks) {
                        t.setDeleted(true);
                    }
                    return Response.status(200).entity("Tasks removed successfully").build();
                } else {
                    return Response.status(404).entity("No tasks found for this user").build();
                }
            } else {
                return Response.status(404).entity("User not found").build();
            }
        } else {
            return Response.status(401).entity("Unauthorized: Only SCRUM_MASTER or PRODUCT_OWNER can access user tasks").build(); // Unauthorized access
        }
    }

    public boolean restoreDeletedTask(int id) {
        TaskEntity t = taskDao.findTaskById(id);
        if (t != null) {
            t.setDeleted(false);
            return true;
        }
        return false;
    }

    public Response removeTaskPermanently(int id, RoleDto user) {
        // Convert integer role to UserRole enum
        UserRole userRole = user.getRole();
        // Check if the user is a PRODUCT_OWNER
        if (userRole == UserRole.PRODUCT_OWNER) {
            TaskEntity t = taskDao.findTaskById(id);
            if (t != null) {
                taskDao.remove(t);
                return Response.status(200).entity("Task removed permanently successfully").build(); // Successful response with tasks;
            } else {
                return Response.status(404).entity("Task not found").build();
            }
        } else {
            return Response.status(401).entity("Unauthorized: Only SCRUM_MASTER or PRODUCT_OWNER can access user tasks").build(); // Unauthorized access
        }
    }

    public TaskDto getTask(int id) {
        TaskEntity t = taskDao.findTaskById(id);
        if (t != null) {
            return convertTaskFromEntityToDto(t);
        } else return null;
    }

    public Response getAllTasks() {
        ArrayList<TaskEntity> tasks = taskDao.findAllTasks();
        if (tasks != null && !tasks.isEmpty()) {
            ArrayList<TaskDto> taskDtos = convertTasksFromEntityListToDtoList(tasks);
            return Response.status(200).entity(taskDtos).build(); // Successful response with tasks
        } else {
            return Response.status(404).entity("No tasks found").build(); // No tasks found
        }
    }

    public Response getUserTasks(LoginDto user, RoleDto roleDto) {
        // Convert integer role to UserRole enum
        UserRole userRole = roleDto.getRole();
        // Check if the user is a SCRUM_MASTER or PRODUCT_OWNER
        if (userRole == UserRole.SCRUM_MASTER || userRole == UserRole.PRODUCT_OWNER) {
            UserEntity userEntity = userDao.findUserByUsername(user.getUsername());
            if (userEntity != null) {
                ArrayList<TaskEntity> tasks = taskDao.findTasksByUser(userEntity);
                if (tasks != null && !tasks.isEmpty()) {
                    ArrayList<TaskDto> taskDtos = convertTasksFromEntityListToDtoList(tasks);
                    return Response.status(200).entity(taskDtos).build(); // Successful response with tasks
                } else {
                    return Response.status(404).entity("No tasks found for this user").build(); // No tasks found
                }
            } else {
                return Response.status(404).entity("User not found").build(); // User not found
            }
        } else {
            return Response.status(401).entity("Unauthorized: Only SCRUM_MASTER or PRODUCT_OWNER can access user tasks").build(); // Unauthorized access
        }
    }

    public Response getDeletedTasks(RoleDto roleDto) {
        // Convert integer role to UserRole enum
        UserRole userRole = roleDto.getRole();
        // Check if the user is a SCRUM_MASTER or PRODUCT_OWNER
        if (userRole == UserRole.SCRUM_MASTER || userRole == UserRole.PRODUCT_OWNER) {
            ArrayList<TaskEntity> tasks = taskDao.findTasksByDeleted();
            if (tasks != null && !tasks.isEmpty()) {
                ArrayList<TaskDto> taskDtos = convertTasksFromEntityListToDtoList(tasks);
                return Response.status(200).entity(taskDtos).build(); // Successful response with tasks
            } else {
                return Response.status(404).entity("No deleted tasks found").build(); // No tasks found
            }
        } else {
            return Response.status(401).entity("Unauthorized: Only SCRUM_MASTER or PRODUCT_OWNER can access user tasks").build(); // Unauthorized access
        }
    }

    public Response getCategoryTasks(CategoryDto category, RoleDto roleDto) {
        // Convert integer role to UserRole enum
        UserRole userRole = roleDto.getRole();
        // Check if the user is a SCRUM_MASTER or PRODUCT_OWNER
        if (userRole == UserRole.SCRUM_MASTER || userRole == UserRole.PRODUCT_OWNER) {
            CategoryEntity ctgEntity = categoryDao.findCategoryById(category.getId());
            if (ctgEntity != null) {
                ArrayList<TaskEntity> tasks = taskDao.getTasksByCategoryId(ctgEntity.getId());
                if (tasks != null && !tasks.isEmpty()) {
                    ArrayList<TaskDto> taskDtos = convertTasksFromEntityListToDtoList(tasks);
                    return Response.status(200).entity(taskDtos).build(); // Successful response with tasks
                } else {
                    return Response.status(404).entity("No tasks found for this category").build(); // No tasks found
                }
            } else {
                return Response.status(404).entity("Category not found").build(); // Category not found
            }
        } else {
            return Response.status(401).entity("Unauthorized: Only SCRUM_MASTER or PRODUCT_OWNER can access user tasks").build(); // Unauthorized access
        }
    }


    public boolean updateTask(TaskDto taskDto, CategoryEntity taskCategory, RoleDto user) {
        // Convert integer role to UserRole enum
        UserRole userRole = user.getRole();
        TaskEntity t;
        // Check if the user is a DEVELOPER
        if (userRole == UserRole.DEVELOPER) {
            t = taskDao.findTaskByIdAndUser(taskDto.getId(), user.getUsername());
        } else {
            t = taskDao.findTaskById(taskDto.getId());
        }
        if (t != null) {
            t.setTitle(taskDto.getTitle());
            t.setDescription(taskDto.getDescription());
            t.setStartDate(taskDto.getStartDate());
            t.setEndDate(taskDto.getEndDate());
            t.setPriority(taskDto.getPriority());
            t.setDeleted(taskDto.isDeleted());
            t.setCategory(taskCategory);
            return true;
        }
        return false;
    }

    public boolean updateTaskStatus(int id, TaskState newStatus) {
        TaskEntity t = taskDao.findTaskById(id);
        if (t != null) {
            t.setState(newStatus);
            return true;
        }
        return false;
    }

    private TaskDto convertTaskFromEntityToDto(TaskEntity t) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(t.getId());
        taskDto.setTitle(t.getTitle());
        taskDto.setDescription(t.getDescription());
        taskDto.setStartDate(t.getStartDate());
        taskDto.setEndDate(t.getEndDate());
        taskDto.setState(t.getState());
        taskDto.setPriority(t.getPriority());
        taskDto.setDeleted(t.isDeleted());
        taskDto.setCategory(t.getCategory().getCategoryName());
        return taskDto;
    }

    private TaskEntity convertTaskFromDtoToEntity(TaskDto t) {
        CategoryEntity taskCategory = categoryDao.findCategoryByName(t.getCategory());
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(t.getTitle());
        taskEntity.setDescription(t.getDescription());
        taskEntity.setPriority(t.getPriority());
        taskEntity.setStartDate(t.getStartDate());
        taskEntity.setEndDate(t.getEndDate());
        taskEntity.setDeleted(t.isDeleted());
        taskEntity.setCategory(taskCategory);
        return taskEntity;
    }

    private ArrayList<TaskDto> convertTasksFromEntityListToDtoList(ArrayList<TaskEntity> taskEntityEntities) {
        ArrayList<TaskDto> taskDtos = new ArrayList<>();
        for (TaskEntity t : taskEntityEntities) {
            taskDtos.add(convertTaskFromEntityToDto(t));
        }
        return taskDtos;
    }
}
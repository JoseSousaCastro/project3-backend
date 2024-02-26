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
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

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

    public boolean removeTask(int id) {
        TaskEntity t = taskDao.findTaskById(id);
        if (t != null) {
            t.setDeleted(true);
            return true;
        }
        return false;
    }

    public boolean removeAllUserTasks(RoleDto user) {
        UserEntity userEntity = userDao.findUserByUsername(user.getUsername());
        if (userEntity != null) {
            ArrayList<TaskEntity> tasks = taskDao.findTasksByUser(userEntity);
            if (tasks != null) {
                for (TaskEntity t : tasks) {
                    t.setDeleted(true);
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean restoreDeletedTask(int id) {
        TaskEntity t = taskDao.findTaskById(id);
        if (t != null) {
            t.setDeleted(false);
            return true;
        }
        return false;
    }

    public boolean removeTaskPermanently(int id) {
        TaskEntity t = taskDao.findTaskById(id);
        if (t != null) {
            taskDao.remove(t);
            return true;
        }
        return false;
    }

    public TaskDto getTask(int id) {
        TaskEntity t = taskDao.findTaskById(id);
        if (t != null) {
            return convertTaskFromEntityToDto(t);
        } else return null;
    }

    public ArrayList<TaskDto> getUserTasks(LoginDto user) {
        UserEntity userEntity = userDao.findUserByUsername(user.getUsername());
        if (userEntity != null) {
            ArrayList<TaskEntity> tasks = taskDao.findTasksByUser(userEntity);
            if (tasks != null) {
                return convertTasksFromEntityListToDtoList(tasks);
            }
            return null;
        }
        return null;
    }


    public ArrayList<TaskDto> getDeletedTasks() {
        ArrayList<TaskEntity> tasks = taskDao.findTasksByDeleted();
        if (tasks != null) {
            return convertTasksFromEntityListToDtoList(tasks);
        }
        return null;
    }


    public ArrayList<TaskDto> getCategoryTasks(CategoryDto category) {
        CategoryEntity ctgEntity = categoryDao.findCategoryById(category.getId());
        if (ctgEntity != null) {
            ArrayList<TaskEntity> tasks = taskDao.getTasksByCategoryId(ctgEntity.getId());
            if (tasks != null) {
                return convertTasksFromEntityListToDtoList(tasks);
            }
        }
        return null;
    }


    public boolean updateOwnTask(int id, TaskDto taskDto, String username) {
        TaskEntity t = taskDao.findTaskByIdAndUser(id, username);
        CategoryEntity taskCategory = categoryDao.findCategoryByName(taskDto.getCategory());
        if (t != null) {
            t.setTitle(taskDto.getTitle());
            t.setDescription(taskDto.getDescription());
            t.setStartDate(taskDto.getStartDate());
            t.setEndDate(taskDto.getEndDate());
            t.setPriority(taskDto.getPriority());
            t.setDeleted(taskDto.getDeleted());
            t.setCategory(taskCategory);
            return true;
        }
        return false;
    }

    public boolean updateTask(int id, TaskDto taskDto) {
        TaskEntity t = taskDao.findTaskById(id);
        CategoryEntity taskCategory = categoryDao.findCategoryByName(taskDto.getCategory());
        if (t != null) {
            t.setTitle(taskDto.getTitle());
            t.setDescription(taskDto.getDescription());
            t.setStartDate(taskDto.getStartDate());
            t.setEndDate(taskDto.getEndDate());
            t.setPriority(taskDto.getPriority());
            t.setDeleted(taskDto.getDeleted());
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
        taskEntity.setDeleted(t.getDeleted());
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


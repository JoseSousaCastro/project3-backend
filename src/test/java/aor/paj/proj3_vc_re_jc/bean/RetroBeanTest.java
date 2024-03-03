package aor.paj.proj3_vc_re_jc.bean;

import aor.paj.proj3_vc_re_jc.dao.*;
import aor.paj.proj3_vc_re_jc.dto.AddCommentDto;
import aor.paj.proj3_vc_re_jc.dto.CreateRetroEventDto;
import aor.paj.proj3_vc_re_jc.entity.*;
import aor.paj.proj3_vc_re_jc.enums.RetroCommentCategory;
import aor.paj.proj3_vc_re_jc.enums.TaskPriority;
import aor.paj.proj3_vc_re_jc.enums.TaskState;
import aor.paj.proj3_vc_re_jc.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class RetroBeanTest {

    RetroBean retroBean;
    RetroEventDao retroEventDao;
    RetroCommentDao retroCommentDao;
    UserDao userDao;
    TaskDao taskDao;
    Set<TaskEntity> userTasksSet;
    List<RetroCommentEntity> retroCommentEntities;
    List<RetroEventEntity> retroEntities;

    @BeforeEach
    void setup() {
        // The class under test
        retroBean = new RetroBean();

        // Creating mock objects
        retroEventDao = mock(RetroEventDao.class);
        retroCommentDao = mock(RetroCommentDao.class);
        userDao = mock(UserDao.class);
        taskDao = mock(TaskDao.class);

        // retroBean uses the mock object previously created
        retroBean.setRetroEventDao(retroEventDao);
        retroBean.setUserDao(userDao);
        retroBean.setRetroCommentDao(retroCommentDao);

        // Preparation: create one user
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("userTest");
        userEntity.setPassword("password");
        userEntity.setTokenId("token_id");
        userEntity.setEmail("test@dei.uc.pt");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setPhone("963963963");
        userEntity.setPhotoURL("https://example.com/profile_photos/random_user123.jpg");
        userEntity.setDeleted(false);
        userEntity.setRole(UserRole.PRODUCT_OWNER);

        // Create one task category
        CategoryEntity ctgEntity = new CategoryEntity();
        ctgEntity.setId(1);
        ctgEntity.setCategoryName("Frontend");

        // Create one task
        TaskEntity tEntity = new TaskEntity();
        tEntity.setId(1);
        tEntity.setTitle("Tarefa_teste1");
        tEntity.setDescription("Esta tarefa serve para testes");
        tEntity.setStartDate("2024-06-12");
        tEntity.setEndDate("2024-06-18");
        tEntity.setPriority(TaskPriority.LOW_PRIORITY);
        tEntity.setCreator(userEntity);
        tEntity.setCategory(ctgEntity);
        tEntity.setState(TaskState.TODO);
        tEntity.setDeleted(false);

        // Create a list of tasks
        userTasksSet = new HashSet<>();
        userTasksSet.add(tEntity);
        userEntity.setTasks(userTasksSet);

        // Create one Retrospective
        RetroEventEntity retroEntity = new RetroEventEntity();
        retroEntity.setEventId(1);
        retroEntity.setTitle("Retrospective_test1");
        retroEntity.setSchedulingDate(LocalDate.of(2024, 6, 12));

        // Create members
        List<UserEntity> members = new ArrayList<>();
        members.add(userEntity);
        retroEntity.setMembers(members);

        // Create a List
        retroEntities = new ArrayList<>();
        retroEntities.add(retroEntity);

        // Create one RetroComment
        RetroCommentEntity retroCommentEntity = new RetroCommentEntity();
        retroCommentEntity.setEvent(retroEntity);
        retroCommentEntity.setUser(userEntity);
        retroCommentEntity.setComment("This is a test comment.");
        retroCommentEntity.setCategory(RetroCommentCategory.STRENGTHS);

        // Add the comment to the RetroEvent
        retroEntity.addComment(retroCommentEntity);

        // Create a List
        retroCommentEntities = new ArrayList<>();
        retroCommentEntities.add(retroCommentEntity);

        // Define behaviours
        when(taskDao.find(0)).thenReturn(null);
        when(taskDao.findTaskById(1)).thenReturn(tEntity);
        when(taskDao.findTaskById(0)).thenReturn(null);
        when(taskDao.findTaskById(5)).thenReturn(null);
        when(userDao.findUserByUsername("userTest")).thenReturn(userEntity);
        when(userDao.findUserByUsername("noUser")).thenReturn(null);
        when(userDao.findUserByToken("token_id")).thenReturn(userEntity);
        when(userDao.findUserByToken("token123")).thenReturn(null);
        when(taskDao.findTasksByDeleted()).thenReturn(null);
        doNothing().when(taskDao).persist(isA(TaskEntity.class));

        doNothing().when(retroCommentDao).saveRetroComment(any(RetroCommentEntity.class));
        doNothing().when(retroCommentDao).updateRetroComment(any(RetroCommentEntity.class));
        doNothing().when(retroCommentDao).deleteRetroComment(anyInt());
        when(retroCommentDao.getRetroCommentById(anyInt())).thenReturn(retroCommentEntity);
        when(retroCommentDao.getAllRetroComments()).thenReturn(retroCommentEntities);
        when(retroEventDao.findEventByTitle(anyString())).thenReturn(retroEntity);
        doNothing().when(retroEventDao).saveRetroEvent(any(RetroEventEntity.class));
        doNothing().when(retroEventDao).addMemberToRetroEvent(anyInt(), any(UserEntity.class));
        doNothing().when(retroEventDao).persist(any(RetroEventEntity.class));
        when(retroEventDao.getAllRetroEvents()).thenReturn(retroEntities);

    }

    @Test
    void testAddRetrospective() {
        // Execution
        boolean result = retroBean.addRetrospective("token", new CreateRetroEventDto());

        // Verification
        assertTrue(result);
        verify(retroEventDao, times(0)).persist(any(RetroEventEntity.class));
    }

    @Test
    void testAddCommentToRetrospective() {
        // Setup
        String token = "token";
        int eventId = 1;
        AddCommentDto temporaryRetroComment = new AddCommentDto();
        temporaryRetroComment.setComment("Test Comment");


        // Mock userDao to return a UserEntity
        UserEntity userEntity = new UserEntity();
        when(userDao.findUserByToken(token)).thenReturn(userEntity);

        // Mock retroEventDao to return a RetroEventEntity
        RetroEventEntity retroEventEntity = new RetroEventEntity();
        when(retroEventDao.find(eventId)).thenReturn(retroEventEntity);

        // Execution
        boolean result = retroBean.addCommentToRetrospective(token, eventId, temporaryRetroComment);

        // Verification
        assertTrue(!result);
        verify(userDao, times(1)).findUserByToken(token);

    }

    @Test
    void testAddMemberToRetrospective() {
        // Setup
        RetroBean retroBean = new RetroBean();
        RetroEventDao retroEventDao = mock(RetroEventDao.class);
        UserDao userDao = mock(UserDao.class);
        retroBean.setRetroEventDao(retroEventDao);
        retroBean.setUserDao(userDao);

        int eventId = 1;
        int userId = 1;

        // Execution
        boolean result = retroBean.addMemberToRetrospective(eventId, userId);

        // Verification
        assertTrue(!result);
        verify(retroEventDao, times(1)).find(eventId);
        verify(userDao, times(1)).find(userId);
    }

    @Test
    void testEditComment() {
        // Setup
        RetroBean retroBean = new RetroBean();
        RetroEventDao retroEventDao = mock(RetroEventDao.class);
        retroBean.setRetroEventDao(retroEventDao);

        int id = 1;
        int id2 = 1;
        AddCommentDto temporaryRetroComment = new AddCommentDto();
        temporaryRetroComment.setComment("Updated Comment");
        temporaryRetroComment.setCategory("2");

        // Execution
        boolean result = retroBean.editComment(id, id2, temporaryRetroComment);

        // Verification
        assertFalse(result);
        verify(retroEventDao, times(1)).find(id);
    }

    @Test
    void testGetRetrospectives() {
        // Setup
        RetroBean retroBean = new RetroBean();
        RetroEventDao retroEventDao = mock(RetroEventDao.class);
        retroBean.setRetroEventDao(retroEventDao);

        // Execution
        List<CreateRetroEventDto> retrospectives = retroBean.getRetrospectives();

        // Verification
        assertNotNull(retrospectives);
        assertEquals(0, retrospectives.size()); // Assuming no retrospectives are returned
        verify(retroEventDao, times(1)).getAllRetroEvents();
    }
}

package aor.paj.proj3_vc_re_jc.bean;

import aor.paj.proj3_vc_re_jc.dao.CategoryDao;
import aor.paj.proj3_vc_re_jc.dao.UserDao;
import aor.paj.proj3_vc_re_jc.entity.CategoryEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import aor.paj.proj3_vc_re_jc.dao.TaskDao;
import aor.paj.proj3_vc_re_jc.entity.TaskEntity;
import aor.paj.proj3_vc_re_jc.entity.UserEntity;
import aor.paj.proj3_vc_re_jc.enums.UserRole;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskBeanTest {

    TaskBean taskBean;
    TaskDao taskDao;
    UserDao userDao;
    CategoryDao categoryDao;
    Set<TaskEntity> tasksSet;
    ArrayList<TaskEntity> tasksList;
    /*

    @BeforeEach
    void setup() {

        // the class under test
        taskBean = new TaskBean();

        // creating mock objects
        taskDao = mock(TaskDao.class);
        userDao = mock(UserDao.class);

        // taskBean uses the mock object previously created
        taskBean.setTaskDao(taskDao);
        taskBean.setUserDao(userDao);
        taskBean.setCategoryDao(categoryDao);

        //Preparation: create one user, one task category, one task and a list of tasks
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
        userEntity.setTasks(tasksSet);


        CategoryEntity ctg = new CategoryEntity();
        a.setId(1);
        a.setTitle("Activity1");
        a.setDescription("This is the first activity");
        a.setOwner(userEntity);

        activitiesSet = new HashSet<ActivityEntity>();
        activitiesList = new ArrayList<ActivityEntity>();
        activitiesSet.add(a);
        activitiesList.add(a);

        userEntity.setActivities(activitiesSet);

        // define behaviour of activityDao
        when(activityDao.find(0)).thenReturn(null);
        when(activityDao.findActivityByUser(userEntity)).thenReturn(activitiesList);
        when(activityDao.findActivityById(1)).thenReturn(a);
        when(activityDao.findActivityById(0)).thenReturn(null);
        when(userDao.findUserByToken("token1")).thenReturn(userEntity);
        when(userDao.findUserByToken("token2")).thenReturn(null);
        doNothing().when(activityDao).persist(isA(ActivityEntity.class));
    }

    @Test
    void test() {
        //fail("this is just a test for showing how a test may fail :)");
        assertTrue(true);
    }

    @Test
    void testGetActivityByID() {
        // tests
        assertEquals(activityBean.getActivity(1).getTitle(), "Activity1");
        assertEquals(activityBean.getActivity(1).getDescription(), "This is the first activity");
        assertNull(activityBean.getActivity(0), "There is no activity with this id, then it should return null");

        // verifications

        // verifies whether findActivityById(0) is called
        verify(activityDao, atLeastOnce()).findActivityById(0);

        // verifies whether findActivityById(1) is called
        verify(activityDao, atLeastOnce()).findActivityById(1);
    }

    @Test
    void testGetActivityByUser() {
        // tests
        assertEquals(1, activityBean.getActivities("token1").size());
        assertNotNull(activityBean.getActivities("token1"), "the list should not be null");
        assertThat(activityBean.getActivities("token1"), hasSize(1));

        // verifications

        // verifies whether findActivityById(0) is called
        verify(activityDao, times(0)).findActivityById(0);
        // verifies whether findActivityById(1) is called
        verify(activityDao, times(0)).findActivityById(1);
    }

    @Test
    void testPersistActivity () {
        // define behaviour
        ActivityEntity activity = new ActivityEntity();
        activity.setId(2);
        activity.setTitle("Activity2");
        activity.setDescription("This is the second activity");
        activity.setOwner(activitiesList.get(0).getOwner());
        activitiesList.add(activity);
        activitiesSet.add(activity);
        // tests
        assertTrue(activityBean.addActivity("token1", new
                ActivityDto(2, "Activity2", "This is the second activity")));
        assertEquals(2, activityBean.getActivities("token1").size());
        assertNotNull(activityBean.getActivities("token1"), "the list should not be null");
        assertThat(activityBean.getActivities("token1"), hasSize(2));

        // verifications
        // verifies whether persist is called
        verify(activityDao, times(1)).persist(isA(ActivityEntity.class));
        // verifies whether findActivityById(1) is called
        verify(activityDao, times(3)).findActivityByUser(isA(UserEntity.class));
    }

 */

}
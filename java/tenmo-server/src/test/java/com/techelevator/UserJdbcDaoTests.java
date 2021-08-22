package com.techelevator;

import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.tags.EditorAwareTag;

import java.util.List;

public class UserJdbcDaoTests extends TenmoDaoTests {

    private static final User HARRY_POTTER = new User(9999L, "harrypotter", "harrypotter", "true");
    private static final User HERMIONE_GRANGER = new User (9991L, "hermionegranger", "hermionegranger", "true");
    private static final User DRACO_MALFOY = new User (9992L, "dracomalfoy", "dracomalfoy", "true");

    private JdbcUserDao userDao;
    private User testUser;

    @Before
    public void setup() {
        userDao = new JdbcUserDao(new JdbcTemplate(dataSource));
        testUser = new User(9993L, "testUser", "password", "true");
    }

    @Test
    public void findIdByUsername_returns_correct_id() {
        long expected = userDao.findIdByUsername("harrypotter");
        long actual = 9999L;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findAll_returns_all_users() {
        List<User> users = userDao.findAll();

        Assert.assertEquals(3, users.size());

        assertUsersMatch(HARRY_POTTER, users.get(0));
        assertUsersMatch(HERMIONE_GRANGER, users.get(1));
        assertUsersMatch(DRACO_MALFOY, users.get(2));
    }

    @Test
    public void findByUsername_returns_user() {
        User user = userDao.findByUsername("harrypotter");

        assertUsersMatch(HARRY_POTTER, user);
    }

    /*@Test
    public void create_creates_new_user() {
        userDao.create(testUser);
    }*/

    @Test
    public void getAccountIdByUserId_returns_accountId() {
        long expected = userDao.getAccountIdByUserId(9999);
        long actual = 1234L;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getUsernameFromAccountId_returns_username() {
        String expected = userDao.getUsernameFromAccountId(1234);
        String actual = "harrypotter";

        Assert.assertEquals(expected, actual);
    }

    private void assertUsersMatch(User expected, User actual) {
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getId(), actual.getId());
    }

}

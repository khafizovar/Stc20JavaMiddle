package part2.lesson17.db.dao;

import org.junit.jupiter.api.*;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson17.db.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author KhafizovR by 08.12.2019
 * Stc20JavaMiddle
 */
class UserDaoTest {

    private static UserDao userDao;
    private static ConnectionManager connectionManager;

    @BeforeEach
    void init() {

    }

    @BeforeAll
    static void setUp() {
        connectionManager = ConnectionManagerJdbcImpl.getInstance();
        userDao = new UserDao(connectionManager);
    }

    @AfterAll
    static void tearDown() {
        try {
            connectionManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void add() {
        assertTrue(userDao.add(new User(-1, "add_test",
                LocalDate.of(2015, Month.JULY, 29),
                "add_test",
                "Orlando",
                "user1@notreal.ru",
                "user1 description")));
    }

    @Test
    void selectByNameAndLoginId() {
        User us = new User(-1, "selectByNameAndLoginId_test",
                LocalDate.of(2015, Month.JULY, 29),
                "selectByNameAndLoginId_test",
                "Orlando",
                "user1@notreal.ru",
                "user1 description");
        assertTrue(userDao.add(us));
        assertNotNull(userDao.selectByNameAndLoginId(us.getName(), us.getLoginId()));
    }

    @Test
    void getById() {
        User us = new User(-1, "getById_test",
                LocalDate.of(2015, Month.JULY, 29),
                "getById_test",
                "Orlando",
                "user1@notreal.ru",
                "user1 description");
        assertTrue(userDao.add(us));
        us = userDao.selectByNameAndLoginId(us.getName(), us.getLoginId());
        assertNotNull(us);
    }


    @Test
    void updateById() {
        User us = new User(-1, "getById_test",
                LocalDate.of(2015, Month.JULY, 29),
                "getById_test",
                "Orlando",
                "user1@notreal.ru",
                "user1 description");
        assertTrue(userDao.add(us));
        us = userDao.selectByNameAndLoginId(us.getName(), us.getLoginId());
        assertNotNull(us);
        us = new User(us.getId(), "updateById_test", LocalDate.of(2015, Month.JULY, 29),
                "updateById_test",
                "Orlando",
                "user1@notreal.ru",
                "user1 description");
        assertTrue(userDao.updateById(us));

    }

    @Test
    void getAll() {
        User us = new User(-1, "getAll_test",
                LocalDate.of(2015, Month.JULY, 29),
                "getAll_test",
                "Orlando",
                "user1@notreal.ru",
                "user1 description");
        assertTrue(userDao.add(us));
        assertTrue(userDao.getAll().size() > 0);
    }

    @Test
    void deleteById() {
        User us = new User(-1, "deleteById_test",
                LocalDate.of(2015, Month.JULY, 29),
                "deleteById_test",
                "Orlando",
                "user1@notreal.ru",
                "user1 description");
        assertTrue(userDao.add(us));
        us = userDao.selectByNameAndLoginId(us.getName(), us.getLoginId());
        assertNotNull(us);
        assertTrue(userDao.deleteById(us.getId()));
    }

    @Test
    void addAll() {
        List<User> addUsers = new ArrayList<>();
        addUsers.add(new User(-1, "addAll_test1",
                LocalDate.of(1995, Month.OCTOBER, 29),
                "_q1",
                "addAll_test1",
                "_q1@notreal.ru",
                "_q1 description"));
        addUsers.add(new User(-1, "addAll_test2",
                LocalDate.of(1995, Month.OCTOBER, 29),
                "addAll_test2",
                "Syktyvkar",
                "_q2@notreal.ru",
                "_q2 description"));
        assertNotNull(userDao.addAll(addUsers));
    }

    @Test
    void addM() {
        assertTrue(userDao.addM(new User(-1, "m11",
                LocalDate.of(2015, Month.JULY, 29),
                "m11",
                "Orlando",
                "m11@notreal.ru",
                "m11 description"), connectionManager.getConnection()));
    }

    @Test
    void truncate() {
            assertDoesNotThrow(() -> userDao.truncate());
            assertTrue(userDao.getAll().size() == 0);
    }
}
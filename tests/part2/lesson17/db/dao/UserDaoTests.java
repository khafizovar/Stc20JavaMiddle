package part2.lesson17.db.dao;


import org.junit.jupiter.api.*;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson17.db.pojo.User;

import javax.swing.text.html.Option;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author KhafizovAR on 16.12.2019.
 * @project Stc20JavaMiddle
 */
class UserDaoTests {

    private UserDao userDao;
    private ConnectionManager connectionManager;

    @BeforeEach
    void init() {
        connectionManager = ConnectionManagerJdbcImpl.getInstance();
        userDao = new UserDao(connectionManager);
        userDao.truncate();

    }

    @AfterEach
    void tearDown() throws SQLException {
        connectionManager.getConnection().close();
    }

    @Test
    void add() {
        User newUser = userDao.add(new User("Иванов Иван Иванович",
                LocalDate.of(2015, Month.JULY, 29),
                "user_addTest",
                "Orlando",
                "user1@notreal.ru",
                "user1 description")).get();
        User fromDb = userDao.getById(newUser.getId()).get();
        assertEquals(newUser.getName(), fromDb.getName());
        assertEquals(newUser.getLoginId(), fromDb.getLoginId());
        assertEquals(newUser.getBirthday(), fromDb.getBirthday());
        assertEquals(newUser.getDescription(),fromDb.getDescription());
        assertEquals(newUser.getEmail(),fromDb.getEmail());
        assertEquals(newUser.getCity(),fromDb.getCity());
    }

    @Test
    void getById() {
        User newUser = userDao.add(new User("Иванов Иван Иванович",
                LocalDate.of(2015, Month.JULY, 29),
                "user_getByIdTest",
                "Orlando",
                "user1@notreal.ru",
                "user1 description")).get();
        assertNotNull(newUser);
        User controlUser = userDao.getById(newUser.getId()).get();
        assertEquals(newUser,controlUser);
    }

    @Test
    void updateById() {
        User newUser = userDao.add(new User("Иванов Иван Иванович",
                LocalDate.of(2015, Month.JULY, 29),
                "user_updateById",
                "Orlando",
                "user1@notreal.ru",
                "user1 description")).get();
        assertNotNull(newUser);
        User updatedUser = userDao.updateById(new User(newUser.getId(),
                newUser.getName(), newUser.getBirthday(), "Василий Джон Макклауд", "Высюкино", newUser.getEmail(), newUser.getDescription())).get();
        User controlUser = userDao.getById(updatedUser.getId()).get();
        assertEquals(updatedUser,controlUser);
    }

    @Test
    void deleteById() {
        User newUser = userDao.add(new User("Иванов Иван Иванович",
                LocalDate.of(2015, Month.JULY, 29),
                "user_deleteById",
                "Orlando",
                "user1@notreal.ru",
                "user1 description")).get();
        assertNotNull(newUser);
        userDao.deleteById(newUser.getId());
        Optional<User> controlUser = userDao.getById(newUser.getId());
        assertFalse(controlUser.isPresent());
    }

    @Test
    void getAll() {
        User newUser = userDao.add(new User("Иванов Иван Иванович",
                LocalDate.of(2015, Month.JULY, 29),
                "user_getAll",
                "Orlando",
                "user1@notreal.ru",
                "user1 description")).get();
        assertTrue(userDao.getAll().size() > 0);

        User fromDb = userDao.getAll().stream().filter(user -> newUser.getId().equals(user.getId()))
                .findAny()
                .orElse(null);
        assertNotNull(fromDb);
        assertEquals(newUser.getName(), fromDb.getName());
        assertEquals(newUser.getLoginId(), fromDb.getLoginId());
        assertEquals(newUser.getBirthday(), fromDb.getBirthday());
        assertEquals(newUser.getDescription(),fromDb.getDescription());
        assertEquals(newUser.getEmail(),fromDb.getEmail());
        assertEquals(newUser.getCity(),fromDb.getCity());
    }

    @Test
    void addAll() {
        List<User> users = new ArrayList<>();
        users.add(new User( "Иванов Иван Иванович",
                LocalDate.of(2015, Month.JULY, 29),
                "user1_addAll",
                "Orlando",
                "user1@notreal.ru",
                "user1 description"));
        users.add(new User( "Сидоров Сидор Сидорович",
                LocalDate.of(1980, Month.AUGUST, 3),
                "user2_addAll",
                "Montreal",
                "user2@notreal.ru",
                "user2 description"));
        users.add(new User("Олегов Олег Олегович",
                LocalDate.of(1955, Month.OCTOBER, 2),
                "user3_addAll",
                "Moscow",
                "user3@notreal.ru",
                "user3 description"));
        users = userDao.addAll(users);
        List<User> allUsers = userDao.getAll();
        assertTrue(allUsers.containsAll(users));
    }

    @Test
    void truncate() {
        User newUser = userDao.add(new User("Иванов Иван Иванович",
                LocalDate.of(2015, Month.JULY, 29),
                "user_truncate",
                "Orlando",
                "user1@notreal.ru",
                "user1 description")).get();
        assertTrue(userDao.truncate());
        assertTrue(userDao.getAll().size() == 0);
    }

    @Test
    void selectByNameAndLoginId() {
        User newUser = userDao.add(new User("Иванов Иван Иванович",
                LocalDate.of(2010, Month.JULY, 29),
                "user_selectByNameAndLoginId",
                "Orlando",
                "user1@notreal.ru",
                "user1 description")).get();
        User controlUser = userDao.selectByNameAndLoginId(newUser.getName(), newUser.getLoginId()).get();
        assertEquals(controlUser, newUser);
    }
}
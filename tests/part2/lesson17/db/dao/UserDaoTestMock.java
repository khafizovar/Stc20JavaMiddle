package part2.lesson17.db.dao;


import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson17.db.pojo.User;

import javax.swing.text.html.Option;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author KhafizovAR on 16.12.2019.
 * @project Stc20JavaMiddle
 */
class UserDaoTestMock {

    private static UserDao userDao;
    private ConnectionManager connectionManager;
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void init() throws SQLException {
        initMocks(this);
        connectionManager = spy(ConnectionManagerJdbcImpl.getInstance());
        connection = mock(Connection.class);
        userDao = new UserDao(connectionManager);
    }

    @AfterAll
    static void tearDown() throws SQLException {

    }

    @Test
    void add() throws SQLException {
        User newUser = new User("Иванов Иван Иванович",
                LocalDate.of(2015, Month.JULY, 29),
                "user_addTest",
                "Orlando",
                "user1@notreal.ru",
                "user1 description");

        assertNotNull(newUser);
        when(connectionManager.getConnection()).thenReturn(connection);
        doReturn(preparedStatement).when(connection).prepareStatement(userDao.INSERT_INTO_PUBLIC_USER_VALUES_DEFAULT);


        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(-1);

        Optional<User> addedUser = userDao.add(newUser);

        verify(connectionManager, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(userDao.INSERT_INTO_PUBLIC_USER_VALUES_DEFAULT);
        verify(preparedStatement, times(1)).setString(1, newUser.getName());
        verify(preparedStatement, times(1)).setObject(2, newUser.getBirthday());
        verify(preparedStatement, times(1)).setString(3, newUser.getLoginId());
        verify(preparedStatement, times(1)).setString(4, newUser.getCity());
        verify(preparedStatement, times(1)).setString(5, newUser.getEmail());
        verify(preparedStatement, times(1)).setString(6, newUser.getDescription());
        verify(preparedStatement, times(1)).executeQuery();
        assertEquals(newUser, addedUser.get());
    }

    /*@Test
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
    }*/

    /*@Test
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
        assertTrue(!controlUser.isPresent());
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
    }

    @Test
    void addAll() {
        List<User> users = new ArrayList<>();
        users.add(userDao.add(new User( "Иванов Иван Иванович",
                LocalDate.of(2015, Month.JULY, 29),
                "user1_addAll",
                "Orlando",
                "user1@notreal.ru",
                "user1 description")).get());
        users.add(userDao.add(new User( "Сидоров Сидор Сидорович",
                LocalDate.of(1980, Month.AUGUST, 3),
                "user2_addAll",
                "Montreal",
                "user2@notreal.ru",
                "user2 description")).get());
        users.add(userDao.add(new User("Олегов Олег Олегович",
                LocalDate.of(1955, Month.OCTOBER, 2),
                "user3_addAll",
                "Moscow",
                "user3@notreal.ru",
                "user3 description")).get());
        users = userDao.addAll(users);
        List<User> allUsers = userDao.getAll();
        System.out.println(users);
        System.out.println(allUsers);
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
    }*/
}
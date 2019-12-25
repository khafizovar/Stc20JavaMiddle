package part2.lesson17.db.dao;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson17.db.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author KhafizovAR on 16.12.2019.
 * @project Stc20JavaMiddle
 */
@ExtendWith({MockitoExtension.class})
class UserDaoTestMock {

    public static final String INSERT_INTO_PUBLIC_USER_VALUES_DEFAULT = "INSERT INTO  public.\"USER\" values (DEFAULT, ?, ?, ?,?,?,?) RETURNING id";

    @InjectMocks
    private UserDao userDao;
    @Spy
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

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
        doReturn(preparedStatement).when(connection).prepareStatement(INSERT_INTO_PUBLIC_USER_VALUES_DEFAULT);


        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(-1);

        Optional<User> addedUser = userDao.add(newUser);

        verify(connectionManager, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(INSERT_INTO_PUBLIC_USER_VALUES_DEFAULT);
        verify(preparedStatement, times(1)).setString(1, newUser.getName());
        verify(preparedStatement, times(1)).setObject(2, newUser.getBirthday());
        verify(preparedStatement, times(1)).setString(3, newUser.getLoginId());
        verify(preparedStatement, times(1)).setString(4, newUser.getCity());
        verify(preparedStatement, times(1)).setString(5, newUser.getEmail());
        verify(preparedStatement, times(1)).setString(6, newUser.getDescription());
        verify(preparedStatement, times(1)).executeQuery();
        assertEquals(newUser, addedUser.get());
    }
}
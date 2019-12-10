package part2.lesson17.db.dao;

import org.junit.jupiter.api.*;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson17.db.pojo.Role;
import part2.lesson17.db.pojo.User;
import part2.lesson17.db.pojo.UserRole;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author KhafizovR by 08.12.2019
 * Stc20JavaMiddle
 */
class UserRoleDaoTest {

    private static RoleDao roleDao;
    private static UserDao userDao;
    private static UserRoleDao userRoleDao;
    private static ConnectionManager connectionManager;
    private static Role testRole;
    private static User testUser;
    private static UserRole userRole;

    @BeforeAll
    static void setUp() {
        connectionManager = ConnectionManagerJdbcImpl.getInstance();
        roleDao = new RoleDao(connectionManager);
        userDao = new UserDao(connectionManager);
        userRoleDao = new UserRoleDao(connectionManager);

        userDao.add(new User(-1, "testUserForUserRoleDaoTesting",   LocalDate.of(2015, Month.JULY, 29),"testUserForUserRoleDaoTestingLogin",
                "TestCity",
                "testUserForUserRoleDaoTesting@notreal.ru",
                "testUserForUserRoleDaoTesting description"));
        testUser = userDao.getAll().stream().filter(item -> item.getName().equals("testUserForUserRoleDaoTesting")).findAny().get();
        roleDao.add(new Role(-1, Role.Roles.CLIENTS, "the role just for test only"));
        testRole = roleDao.getAll().stream().filter(item -> item.getDescription().equals("the role just for test only")).findAny().get();
    }

    @AfterAll
    static void tearDown() {
        try {
            connectionManager.getConnection().close();
            assertTrue(userDao.deleteById(testUser.getId()));
            assertTrue(roleDao.deleteById(testRole.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void add() {
        assertTrue(userRoleDao.add(new UserRole(-1, testUser.getId(), testRole.getId())));
        UserRole ur =  userRoleDao.getAll().stream().filter(item -> item.getRoleId().equals(testRole.getId()) && item.getUserId().equals(testUser.getId())).findAny().get();
        assertTrue(userRoleDao.deleteById(ur.getId()));
    }

    @Test
    void getById() {
        assertTrue(userRoleDao.add(new UserRole(-1, testUser.getId(), testRole.getId())));
        UserRole ur =  userRoleDao.getAll().stream().filter(item -> item.getRoleId().equals(testRole.getId()) && item.getUserId().equals(testUser.getId())).findAny().get();
        assertTrue(userRoleDao.deleteById(ur.getId()));
    }

    @Test
    void updateById() {
        assertTrue(userRoleDao.add(new UserRole(-1, testUser.getId(), testRole.getId())));
        UserRole ur =  userRoleDao.getAll().stream().filter(item -> item.getRoleId().equals(testRole.getId()) && item.getUserId().equals(testUser.getId())).findAny().get();
        assertTrue(userRoleDao.deleteById(ur.getId()));
    }

    @Test
    void deleteById() {
        assertTrue(userRoleDao.add(new UserRole(-1, testUser.getId(), testRole.getId())));
        UserRole ur =  userRoleDao.getAll().stream().filter(item -> item.getRoleId().equals(testRole.getId()) && item.getUserId().equals(testUser.getId())).findAny().get();
        assertTrue(userRoleDao.deleteById(ur.getId()));
    }

    @Test
    void getAll() {
        assertTrue(userRoleDao.add(new UserRole(-1, testUser.getId(), testRole.getId())));
        assertTrue(userRoleDao.getAll().size() > 0);
        UserRole ur =  userRoleDao.getAll().stream().filter(item -> item.getRoleId().equals(testRole.getId()) && item.getUserId().equals(testUser.getId())).findAny().get();
        assertTrue(userRoleDao.deleteById(ur.getId()));
    }

    @Test
    void truncate() {
        assertDoesNotThrow(() -> roleDao.truncate());
        assertTrue(roleDao.getAll().size() == 0);
    }

    @Test
    void addM() {
        assertTrue(userRoleDao.addM(new UserRole(-1, testUser.getId(), testRole.getId()), connectionManager.getConnection()));
        UserRole ur =  userRoleDao.getAll().stream().filter(item -> item.getRoleId().equals(testRole.getId()) && item.getUserId().equals(testUser.getId())).findAny().get();
        assertNotNull(testRole);
        assertTrue(userRoleDao.deleteById(testRole.getId()));
    }
}
package part2.lesson17.db.dao;

import org.junit.jupiter.api.*;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson17.db.pojo.Role;
import part2.lesson17.db.pojo.User;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author KhafizovR by 08.12.2019
 * Stc20JavaMiddle
 */
class RoleDaoTest {

    private static RoleDao roleDao;
    private static ConnectionManager connectionManager;
    private static Role testRole;

    @BeforeAll
    static void setUp() {
        connectionManager = ConnectionManagerJdbcImpl.getInstance();
        roleDao = new RoleDao(connectionManager);
    }

    @AfterAll
    static void tearDown() {
        try {
            connectionManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @BeforeEach
    void init() {
        roleDao.add(new Role(-1, Role.Roles.CLIENTS, "the role just for test only"));
        testRole = roleDao.getAll().stream().filter(item -> item.getDescription().equals("the role just for test only")).findAny().get();
    }
    @AfterEach
    void clean() {
        if(testRole != null && testRole.getId() > -1)
            assertTrue(roleDao.deleteById(testRole.getId()));
    }

    @Test
    void add() {
        assertTrue(roleDao.add(new Role(-1, Role.Roles.CLIENTS, "just test role, add method")));
        testRole = roleDao.getAll().stream().filter(item -> item.getDescription().equals("just test role, add method")).findAny().get();
        assertNotNull(testRole);
        assertTrue(roleDao.deleteById(testRole.getId()));
    }

    @Test
    void getById() {
        assertNotNull(roleDao.getById(testRole.getId()));
    }

    @Test
    void updateById() {
        Role role = new Role(testRole.getId(), Role.Roles.ADMINISTRATION, "just test role, updateById method");
        assertTrue(roleDao.updateById(role));
    }

    @Test
    void deleteById() {
        assertTrue(roleDao.deleteById(testRole.getId()));
        testRole = null;
    }

    @Test
    void getAll() {
        assertTrue(roleDao.getAll().size() > 0);
    }

    @Test
    void addAll() {
        Role r1 = new Role(-1, Role.Roles.BILLING, "test role, method addAll");
        assertTrue(roleDao.addAll(Arrays.asList(r1)));
        Role forDel = roleDao.getAll().stream().filter(r -> r.getDescription().equals(r1.getDescription())).findFirst().get();
        assertTrue(roleDao.deleteById(forDel.getId()));
    }

    @Test
    void truncate() {
        assertDoesNotThrow(() -> roleDao.truncate());
        assertTrue(roleDao.getAll().size() == 0);
    }

    @Test
    void addM() {
        assertTrue(roleDao.addM(new Role(-1, Role.Roles.CLIENTS, "just test role, add method"), connectionManager.getConnection()));
        testRole = roleDao.getAll().stream().filter(item -> item.getDescription().equals("just test role, add method")).findAny().get();
        assertNotNull(testRole);
        assertTrue(roleDao.deleteById(testRole.getId()));
    }
}
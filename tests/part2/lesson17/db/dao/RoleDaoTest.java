package part2.lesson17.db.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author KhafizovR by 08.12.2019
 * Stc20JavaMiddle
 */
class RoleDaoTest {

    private static RoleDao roleDao;
    private static ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        connectionManager = ConnectionManagerJdbcImpl.getInstance();
        roleDao = new RoleDao(connectionManager);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void add() {
    }

    @Test
    void getById() {
    }

    @Test
    void updateById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void addAll() {
    }

    @Test
    void truncate() {
    }

    @Test
    void addM() {
    }
}
package part2.lesson16.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson16.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson16.db.dao.GenericDao;
import part2.lesson16.db.dao.RoleDao;
import part2.lesson16.db.dao.UserDao;
import part2.lesson16.db.dao.UserRoleDao;
import part2.lesson16.db.pojo.Role;
import part2.lesson16.db.pojo.User;
import part2.lesson16.db.pojo.UserRole;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Взять за основу ДЗ_15,
 * покрыть код логированием
 * в основных блоках try покрыть уровнем INFO
 * с исключениях catch покрыть уровнем ERROR
 * настроить конфигурацию логера, что бы все логи записывались в БД, таблица LOGS,
 * колонки ID, DATE, LOG_LEVEL, MESSAGE, EXCEPTION
 */
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        GenericDao<User> userDao = new UserDao();
        GenericDao<Role> roleDao = new RoleDao();
        GenericDao<UserRole> urDao = new UserRoleDao();

        //Сперва очистим все таблицы
        urDao.truncate();
        roleDao.truncate();
        userDao.truncate();
        logger.info("Задание 2-а");
        //Заполняем пользователей
        userDao.add(new User(-1, "user1",
                LocalDate.of(2015, Month.JULY, 29),
                "user1",
                "Orlando",
                "user1@notreal.ru",
                "user1 description"));
        userDao.add(new User(-1, "user2",
                LocalDate.of(1980, Month.AUGUST, 3),
                "user2",
                "Montreal",
                "user2@notreal.ru",
                "user2 description"));
        userDao.add(new User(-1, "user3",
                LocalDate.of(1955, Month.OCTOBER, 2),
                "user3",
                "Moscow",
                "user3@notreal.ru",
                "user3 description"));

        logger.info("Задание 2-б");
        //Заполняем роли через batch
        List<Role> addRoles = new ArrayList<>();

        addRoles.add(new Role(-1, Role.Roles.ADMINISTRATION, "Администраторы"));
        addRoles.add(new Role(-1, Role.Roles.CLIENTS, "Клиенты"));
        addRoles.add(new Role(-1, Role.Roles.BILLING, "Биллинг"));
        roleDao.addAll(addRoles);

        Role adminRole = null;
        Role userRole = null;
        Role biilingRole = null;
        //Выдаем всем клиента
        for(Role role: roleDao.getAll()) {
            switch (role.getName()) {
                case ADMINISTRATION: {
                    adminRole = role;
                    break;
                }
                case CLIENTS: {
                    userRole = role;
                    break;
                }
                case BILLING: {
                    biilingRole = role;
                    break;
                }
            }
        }

        //Первому выдаем админа
        List<User> users = userDao.getAll();
        urDao.add(new UserRole(-1, users.get(0).getId(), adminRole.getId()));

        //Последнему выдаем биллинга
        urDao.add(new UserRole(-1, users.get(users.size() -1 ).getId(), biilingRole.getId()));
        logger.info("Задание 3");
        logger.info(((UserDao)userDao).selectByNameAndLoginId("user1", "user1").toString());

        logger.info("Задание 4-a");
        Connection conn = ConnectionManagerJdbcImpl.getInstance().getConnection();
        conn.setAutoCommit(false);
        userDao.add(new User(-1, "user555",
                LocalDate.of(2099, Month.JULY, 29),
                "user555",
                "Orlando",
                "user555@notreal.ru",
                "user555 description"));
        Savepoint sv = conn.setSavepoint("A");
        roleDao.add(new Role(-1, Role.Roles.ADMINISTRATION, "Администраторы группа 2"));
        //Обновим
        users = userDao.getAll();
        //Всем выдаем роль клиента
        for (User user: users) {
            urDao.add(new UserRole(-1, user.getId(), userRole.getId()));
        }
        //коммитим
        conn.commit();

        logger.info("Задание 4-б");

        conn.setAutoCommit(false);
        userDao.add(new User(-1, "user777",
                LocalDate.of(2001, Month.JULY, 29),
                "user777",
                "Oslo",
                "user777@notreal.ru",
                "user777 description"));

        // Create Savepoint
        Savepoint sp = conn.setSavepoint("A");
        //Формируем неверные ключи
        urDao.add(new UserRole(-1, Integer.MAX_VALUE, userRole.getId()));
        //Делаем коммит
        conn.commit();
        conn.close();
        //Смотрим список пользователей пользователей (Пользователь user777 присутствует)
        for (User user: userDao.getAll()) {
            logger.info(user);
        }
    }
}

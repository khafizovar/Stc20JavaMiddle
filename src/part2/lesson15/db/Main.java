package part2.lesson15.db;

import part2.lesson15.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson15.db.dao.GenericDao;
import part2.lesson15.db.dao.RoleDao;
import part2.lesson15.db.dao.UserDao;
import part2.lesson15.db.dao.UserRoleDao;
import part2.lesson15.db.pojo.Role;
import part2.lesson15.db.pojo.User;
import part2.lesson15.db.pojo.UserRole;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 1)    Спроектировать базу
 * -      Таблица USER содержит поля id, name, birthday, login_ID, city, email, description
 * -      Таблица ROLE содержит поля id, name (принимает значения Administration, Clients, Billing), description
 * -      Таблица USER_ROLE содержит поля id, user_id, role_id
 *        Типы полей на ваше усмотрению, возможно использование VARCHAR(255)
 * 2)      Через jdbc интерфейс сделать запись данных(INSERT) в таблицу
 *      a)      Используя параметризированный запрос
 *      b)      Используя batch процесс
 * 3)      Сделать параметризированную выборку по login_ID и name одновременно
 * 4)      Перевести connection в ручное управление транзакциями
 *      a)      Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE) между sql операциями установить логическую точку сохранения(SAVEPOINT)
 *      б)      Выполнить 2-3 SQL операции на ваше усмотрение (например, Insert в 3 таблицы – USER, ROLE, USER_ROLE) между sql операциями установить точку сохранения (SAVEPOINT A), намеренно ввести некорректные данные на последней операции, что бы транзакция откатилась к логической точке SAVEPOINT A
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        GenericDao<User> userDao = new UserDao();
        GenericDao<Role> roleDao = new RoleDao();
        GenericDao<UserRole> urDao = new UserRoleDao();

        //Сперва очистим все таблицы
        urDao.truncate();
        roleDao.truncate();
        userDao.truncate();
        System.out.println("Задание 2-а");
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

        System.out.println("Задание 2-б");
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
        System.out.println("Задание 3");
        System.out.println(((UserDao)userDao).selectByNameAndLoginId("user1", "user1").toString());

        System.out.println("Задание 4-a");
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

        System.out.println("Задание 4-б");

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
            System.out.println(user);
        }
    }
}

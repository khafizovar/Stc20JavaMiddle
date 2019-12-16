package part2.lesson17.db.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson17.db.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public class UserDao implements GenericDao<User> {

    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public static final String INSERT_INTO_PUBLIC_USER_VALUES_DEFAULT = "INSERT INTO  public.\"USER\" values (DEFAULT, ?, ?, ?,?,?,?) RETURNING id";
    public static final String SELECT_FROM_PUBLIC_USER_WHERE_ID = "SELECT * FROM public.\"USER\" WHERE id = ?";
    public static final String UPDATE_PUBLIC_USER_SET_NAME_BIRTHDAY_LOGIN_ID_CITY_EMAIL_DESCRIPTION_WHERE_ID = "UPDATE  public.\"USER\" SET name=?, birthday=?, \"login_ID\"=?, city=?, email=?, description=? WHERE id=?";
    public static final String DELETE_FROM_PUBLIC_USER_WHERE_ID = "DELETE FROM  public.\"USER\" WHERE id=?";
    public static final String SELECT_FROM_PUBLIC_USER = "SELECT * FROM  public.\"USER\"";
    public static final String INSERT_INTO_PUBLIC_USER_VALUES_DEFAULT1 = "INSERT INTO  public.\"USER\" values (DEFAULT, ?, ?, ?,?,?,?)";
    public static final String TRUNCATE_TABLE_PUBLIC_USER_CASCADE = "truncate table  public.\"USER\" cascade";
    public static final String SELECT_FROM_PUBLIC_USER_WHERE_NAME_AND_LOGIN_ID = "SELECT * FROM public.\"USER\" WHERE name = ? and \"login_ID\" = ?";

    private ConnectionManager connectionManager;

    public UserDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<User> add(User user) {
        logger.info("Add User" + user.toString());
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_PUBLIC_USER_VALUES_DEFAULT);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setObject(2, user.getBirthday());
            preparedStatement.setString(3, user.getLoginId());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return Optional.of(new User(resultSet.getInt(1),
                    user.getName(),
                    user.getBirthday(),
                    user.getLoginId(),
                    user.getCity(),
                    user.getEmail(),
                    user.getDescription()));
        } catch (SQLException e) {
            logger.error("Exception in add(User):" + user.toString(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getById(Integer id) {
        logger.info("getById User" + id);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_FROM_PUBLIC_USER_WHERE_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getObject(3, LocalDate.class),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Exception in getById(User) Id:" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> updateById(User user) {
        logger.info("updateById User" + user.toString());
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_PUBLIC_USER_SET_NAME_BIRTHDAY_LOGIN_ID_CITY_EMAIL_DESCRIPTION_WHERE_ID);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setObject(2, user.getBirthday());
            preparedStatement.setString(3, user.getLoginId());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            preparedStatement.setInt(7, user.getId());
            System.out.println(preparedStatement.executeUpdate());
            return this.getById(user.getId());
        } catch (SQLException e) {
            logger.error("Exception in updateById(User) User:" + user.toString(), e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) {
        logger.info("deleteById User" + id);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    DELETE_FROM_PUBLIC_USER_WHERE_ID);
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error("Exception in deleteById(User) Id:" + id, e);
            return false;
        }
        return true;
    }

    @Override
    public List<User> getAll() {
        logger.info("getAll");
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_FROM_PUBLIC_USER);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getObject(3, LocalDate.class),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Exception in getAll(Users)", e);
        }
        return users;
    }

    @Override
    public List<User> addAll(List<User> objs) {
        logger.info("addAll" + objs);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_PUBLIC_USER_VALUES_DEFAULT1);
            for (User user: objs) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setObject(2, user.getBirthday());
                preparedStatement.setString(3, user.getLoginId());
                preparedStatement.setString(4, user.getCity());
                preparedStatement.setString(5, user.getEmail());
                preparedStatement.setString(6, user.getDescription());
                preparedStatement.addBatch();
            }
            System.out.println(preparedStatement.executeBatch());
            List<User> res = new ArrayList<User>();
            for(User source: objs) {
                if(this.selectByNameAndLoginId(source.getName(), source.getLoginId()).isPresent()) {
                    res.add(source);
                }
            }
            return res;
        } catch (SQLException e) {
            logger.error("Exception in addAll(Users) List<User>:" + objs.toString(), e);
        }
        return  new ArrayList<User>();
    }

    @Override
    public boolean truncate() {
        logger.info("truncate");
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    TRUNCATE_TABLE_PUBLIC_USER_CASCADE);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Exception in truncate(User)", e);
            return false;
        }
        return true;
    }

    /**
     * Выборка пользователя по имени и логинИд
     * @param name
     * @param loginId
     * @return
     */
    public Optional<User> selectByNameAndLoginId(String name, String loginId) {
        logger.info("selectByNameAndLoginId:" + name + ";"+ loginId);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_FROM_PUBLIC_USER_WHERE_NAME_AND_LOGIN_ID);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, loginId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getObject(3, LocalDate.class),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.error("Exception in selectByNameAndLoginId name:" + name + "; loginId: " + loginId, e);
        }
        return Optional.empty();
    }
}

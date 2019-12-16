package part2.lesson17.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson17.db.pojo.UserRole;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public class UserRoleDao implements GenericDao<UserRole> {

    private static final Logger logger = LogManager.getLogger(UserRoleDao.class);

    public static final String INSERT_INTO_PUBLIC_USER_ROLE_VALUES_DEFAULT = "INSERT INTO  public.\"USER_ROLE\" values (DEFAULT, ?, ?) RETURNING id";
    public static final String SELECT_FROM_PUBLIC_USER_ROLE_WHERE_ID = "SELECT * FROM  public.\"USER_ROLE\" WHERE id = ?";
    public static final String UPDATE_PUBLIC_USER_ROLE_SET_USER_ID_ROLE_ID_WHERE_ID = "UPDATE  public.\"USER_ROLE\" SET user_id=?, role_id=? WHERE id=?";
    public static final String DELETE_FROM_PUBLIC_USER_ROLE_WHERE_ID = "DELETE FROM  public.\"USER_ROLE\" WHERE id=?";
    public static final String SELECT_FROM_PUBLIC_USER_ROLE = "SELECT * FROM  public.\"USER_ROLE\"";
    public static final String TRUNCATE_TABLE_PUBLIC_USER_ROLE = "truncate table  public.\"USER_ROLE\"";

    private ConnectionManager connectionManager;

    public UserRoleDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<UserRole> add(UserRole ur) {
        logger.info("add(UserRole):" + ur);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_PUBLIC_USER_ROLE_VALUES_DEFAULT);
            preparedStatement.setInt(1, ur.getUserId());
            preparedStatement.setInt(2, ur.getRoleId());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(new UserRole(resultSet.getInt(1),
                    ur.getUserId(),
                    ur.getRoleId()));
        } catch (SQLException e) {
            logger.error("Exception in add(UserRole) UserRole:" + ur.toString(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserRole> getById(Integer id) {
        logger.info("getById:" + id);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_FROM_PUBLIC_USER_ROLE_WHERE_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserRole ur = new UserRole(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3));
                return Optional.of(ur);
            }
        } catch (SQLException e) {
            logger.error("Id:" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserRole> updateById(UserRole ur) {
        logger.info("updateById:" + ur);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_PUBLIC_USER_ROLE_SET_USER_ID_ROLE_ID_WHERE_ID);
            preparedStatement.setInt(1, ur.getUserId());
            preparedStatement.setInt(2, ur.getRoleId());
            preparedStatement.setInt(3, ur.getId());
            System.out.println(preparedStatement.executeUpdate());
            return this.getById(ur.getId());
        } catch (SQLException e) {
            logger.error("Exception in getById(UserRole) UserRole:" + ur.toString(), e);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) {
        logger.info("deleteById:" + id);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    DELETE_FROM_PUBLIC_USER_ROLE_WHERE_ID);
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error("Id:" + id, e);
            return false;
        }
        return true;
    }

    @Override
    public List<UserRole> getAll() {
        logger.info("getAll");
        List<UserRole> urs = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_FROM_PUBLIC_USER_ROLE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserRole ur = new UserRole(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3));
                urs.add(ur);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return urs;
    }

    @Override
    public boolean truncate() {
        logger.info("truncate");
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    TRUNCATE_TABLE_PUBLIC_USER_ROLE);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public List<UserRole> addAll(List<UserRole> objs) {
        throw new NotImplementedException();
    }
}

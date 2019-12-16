package part2.lesson16.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson16.db.ConnectionManager.ConnectionManager;
import part2.lesson16.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson16.db.pojo.UserRole;
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

    private static ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    @Override
    public Optional<UserRole> add(UserRole ur) {
        logger.info("add(UserRole):" + ur);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO  public.\"USER_ROLE\" values (DEFAULT, ?, ?) RETURNING id");
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
        logger.info("getById(UserRole):" + id);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM  public.\"USER_ROLE\" WHERE id = ?");
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
            logger.error("Exception in getById(UserRole) Id:" + id, e);
        }
        return null;
    }

    @Override
    public Optional<UserRole> updateById(UserRole ur) {
        logger.info("updateById(UserRole):" + ur);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE  public.\"USER_ROLE\" SET user_id=?, role_id=?" +
                            "WHERE id=?");
            preparedStatement.setInt(1, ur.getUserId());
            preparedStatement.setInt(2, ur.getRoleId());
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
                    "DELETE FROM  public.\"USER_ROLE\" WHERE id=?");
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error("Exception in deleteById(UserRole)Id:" + id, e);
            return false;
        }
        return true;
    }

    @Override
    public List<UserRole> getAll() {
        logger.info("getAll(UserRole)");
        List<UserRole> urs = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM  public.\"USER_ROLE\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                UserRole ur = new UserRole(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3));
                urs.add(ur);
            }
        } catch (SQLException e) {
            logger.error("Exception in getAll(UserRole)", e);
        }
        return urs;
    }

    @Override
    public boolean truncate() {
        logger.info("truncate");
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "truncate table  public.\"USER_ROLE\"");
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error("Exception in truncate(UserRole)", e);
            return false;
        }
        return true;
    }

    @Override
    public List<UserRole> addAll(List<UserRole> objs) {
        throw new NotImplementedException();
    }
}

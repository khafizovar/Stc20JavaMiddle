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

/**
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public class UserRoleDao implements GenericDao<UserRole> {

    private static final Logger logger = LogManager.getLogger(UserRoleDao.class);

    private static ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    @Override
    public boolean add(UserRole ur) {
        logger.info("add:" + ur);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO  public.\"USER_ROLE\" values (DEFAULT, ?, ?)");
            preparedStatement.setInt(1, ur.getUserId());
            preparedStatement.setInt(2, ur.getRoleId());
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error(ur.toString(), e);
            return false;
        }
        return true;
    }

    @Override
    public UserRole getById(Integer id) {
        logger.info("getById:" + id);
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
                return ur;
            }
        } catch (SQLException e) {
            logger.error("Id:" + id, e);
        }
        return null;
    }

    @Override
    public boolean updateById(UserRole ur) {
        logger.info("updateById:" + ur);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE  public.\"USER_ROLE\" SET user_id=?, role_id=?" +
                            "WHERE id=?");
            preparedStatement.setInt(1, ur.getUserId());
            preparedStatement.setInt(2, ur.getRoleId());
            System.out.println(preparedStatement.executeUpdate());
            return true;
        } catch (SQLException e) {
            logger.error(ur.toString(), e);
        }
        return false;
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
            logger.error(e);
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
            logger.error(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(List<UserRole> objs) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addM(UserRole ur, Connection conn) {
        logger.info("addM:" + ur);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO  public.\"USER_ROLE\" values (DEFAULT, ?, ?)");
            preparedStatement.setInt(1, ur.getUserId());
            preparedStatement.setInt(2, ur.getRoleId());
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error(ur.toString(), e);
            return false;
        }
        return true;
    }
}

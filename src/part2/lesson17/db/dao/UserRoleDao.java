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

/**
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public class UserRoleDao implements GenericDao<UserRole> {

    private static final Logger logger = LogManager.getLogger(UserRoleDao.class);

    public static final String INSERT_INTO_PUBLIC_USER_ROLE_VALUES_DEFAULT = "INSERT INTO  public.\"USER_ROLE\" values (DEFAULT, ?, ?)";
    public static final String SELECT_FROM_PUBLIC_USER_ROLE_WHERE_ID = "SELECT * FROM  public.\"USER_ROLE\" WHERE id = ?";
    public static final String UPDATE_PUBLIC_USER_ROLE_SET_USER_ID_ROLE_ID_WHERE_ID = "UPDATE  public.\"USER_ROLE\" SET user_id=?, role_id=? WHERE id=?";
    public static final String DELETE_FROM_PUBLIC_USER_ROLE_WHERE_ID = "DELETE FROM  public.\"USER_ROLE\" WHERE id=?";
    public static final String SELECT_FROM_PUBLIC_USER_ROLE = "SELECT * FROM  public.\"USER_ROLE\"";
    public static final String TRUNCATE_TABLE_PUBLIC_USER_ROLE = "truncate table  public.\"USER_ROLE\"";
    public static final String INSERT_INTO_PUBLIC_USER_ROLE_VALUES_DEFAULT1 = "INSERT INTO  public.\"USER_ROLE\" values (DEFAULT, ?, ?)";

    private ConnectionManager connectionManager;

    public UserRoleDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean add(UserRole ur) {
        logger.info("add:" + ur);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_PUBLIC_USER_ROLE_VALUES_DEFAULT);
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
                    SELECT_FROM_PUBLIC_USER_ROLE_WHERE_ID);
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
                    UPDATE_PUBLIC_USER_ROLE_SET_USER_ID_ROLE_ID_WHERE_ID);
            preparedStatement.setInt(1, ur.getUserId());
            preparedStatement.setInt(2, ur.getRoleId());
            preparedStatement.setInt(3, ur.getId());
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
    public boolean addAll(List<UserRole> objs) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addM(UserRole ur, Connection conn) {
        logger.info("addM:" + ur);
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    INSERT_INTO_PUBLIC_USER_ROLE_VALUES_DEFAULT1);
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

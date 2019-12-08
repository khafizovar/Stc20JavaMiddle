package part2.lesson16.db.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson16.db.ConnectionManager.ConnectionManager;
import part2.lesson16.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson16.db.pojo.Role;

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
public class RoleDao implements GenericDao<Role> {

    private static final Logger logger = LogManager.getLogger(RoleDao.class);

    private static ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    @Override
    public boolean add(Role role) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO  public.\"ROLE\" values (DEFAULT, ?, ?)");
            preparedStatement.setString(1, role.getName().name());
            preparedStatement.setString(2, role.getDescription());
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error(role.toString(), e);
            return false;
        }
        return true;
    }

    @Override
    public Role getById(Integer id) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM  public.\"ROLE\" WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt(1),
                        Role.Roles.valueOf(resultSet.getString(2)),
                        resultSet.getString(3));
                return role;
            }
        } catch (SQLException e) {
            logger.error("Id:" + id, e);
        }
        return null;
    }

    @Override
    public boolean updateById(Role role) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE  public.\"ROLE\" SET name=?, description=?" +
                            "WHERE id=?");
            preparedStatement.setString(1, role.getName().name());
            preparedStatement.setString(2, role.getDescription());
            System.out.println(preparedStatement.executeUpdate());
            return true;
        } catch (SQLException e) {
            logger.error(role.toString(), e);
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM  public.\"ROLE\" WHERE id=?");
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error("Id:" + id, e);
            return false;
        }
        return true;
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM  public.\"ROLE\"");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt(1),
                        Role.Roles.valueOf(resultSet.getString(2)),
                        resultSet.getString(3));
                roles.add(role);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return roles;
    }

    @Override
    public boolean addAll(List<Role> objs) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO  public.\"ROLE\" values (DEFAULT, ?, ?)");
            for (Role role: objs) {
                preparedStatement.setString(1, role.getName().name());
                preparedStatement.setString(2, role.getDescription());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            logger.error(objs.toString(), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean truncate() {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "truncate table  public.\"ROLE\" cascade ");
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addM(Role role, Connection conn) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO  public.\"ROLE\" values (DEFAULT, ?, ?)");
            preparedStatement.setString(1, role.getName().name());
            preparedStatement.setString(2, role.getDescription());
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error(role.toString(), e);
            return false;
        }
        return true;
    }
}
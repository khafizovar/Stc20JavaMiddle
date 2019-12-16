package part2.lesson17.db.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import part2.lesson17.db.ConnectionManager.ConnectionManager;
import part2.lesson17.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson17.db.pojo.Role;

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
public class RoleDao implements GenericDao<Role> {

    private static final Logger logger = LogManager.getLogger(RoleDao.class);

    public static final String UPDATE_PUBLIC_ROLE_SET_NAME_DESCRIPTION_WHERE_ID = "UPDATE  public.\"ROLE\" SET name=?, description=? WHERE id=? RETURNING id";
    public static final String INSERT_INTO_PUBLIC_ROLE_VALUES_DEFAULT = "INSERT INTO  public.\"ROLE\" values (DEFAULT, ?, ?) RETURNING id";
    public static final String SELECT_FROM_PUBLIC_ROLE_WHERE_ID = "SELECT * FROM  public.\"ROLE\" WHERE id = ?";
    public static final String DELETE_FROM_PUBLIC_ROLE_WHERE_ID = "DELETE FROM  public.\"ROLE\" WHERE id=?";
    public static final String SELECT_FROM_PUBLIC_ROLE = "SELECT * FROM  public.\"ROLE\"";
    public static final String INSERT_INTO_PUBLIC_ROLE_VALUES_DEFAULT1 = "INSERT INTO  public.\"ROLE\" values (DEFAULT, ?, ?)";
    public static final String TRUNCATE_TABLE_PUBLIC_ROLE_CASCADE = "truncate table  public.\"ROLE\" cascade";
    public static final String INSERT_INTO_PUBLIC_ROLE_VALUES_DEFAULT2 = "INSERT INTO  public.\"ROLE\" values (DEFAULT, ?, ?)";

    private ConnectionManager connectionManager;

    public RoleDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Optional<Role> add(Role role) {
        logger.info("addM:" + role.toString());
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_PUBLIC_ROLE_VALUES_DEFAULT);
            preparedStatement.setString(1, role.getName().name());
            preparedStatement.setString(2, role.getDescription());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(new Role(resultSet.getInt(1),
                    role.getName(),
                    role.getDescription()));
        } catch (SQLException e) {
            logger.error("Exception in add(Role):" + role.toString(), e);
        }
        return Optional.empty();
    }

    @Override
    public  Optional<Role> getById(Integer id) {
        logger.info("getById:" + id);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_FROM_PUBLIC_ROLE_WHERE_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt(1),
                        Role.Roles.valueOf(resultSet.getString(2)),
                        resultSet.getString(3));
                return Optional.of(role);
            }
        } catch (SQLException e) {
            logger.error("Exception in getById(Role) Id:" + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Role> updateById(Role role) {
        logger.info("updateById:" + role);

        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    UPDATE_PUBLIC_ROLE_SET_NAME_DESCRIPTION_WHERE_ID);
            preparedStatement.setString(1, role.getName().name());
            preparedStatement.setString(2, role.getDescription());
            preparedStatement.setInt(3, role.getId());
            System.out.println(preparedStatement.executeUpdate());
            return this.getById(role.getId());
        } catch (SQLException e) {
            logger.error("Exception in updateById(Role):" + role.toString(), e);
        }
        return Optional.empty();
    }


    @Override
    public boolean deleteById(Integer id) {
        logger.info("deleteById:" + id);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    DELETE_FROM_PUBLIC_ROLE_WHERE_ID);
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            logger.error("Exception in deleteById(Role) Id:" + id, e);
            return false;
        }
        return true;
    }

    @Override
    public List<Role> getAll() {
        logger.info("getAll(Role)");
        List<Role> roles = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    SELECT_FROM_PUBLIC_ROLE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role(
                        resultSet.getInt(1),
                        Role.Roles.valueOf(resultSet.getString(2)),
                        resultSet.getString(3));
                roles.add(role);
            }
        } catch (SQLException e) {
            logger.error("Exception in getAll(Role)", e);
        }
        return roles;
    }

    @Override
    public List<Role> addAll(List<Role> objs) {
        logger.info("addAll(Role): List<Role>:" + objs);
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    INSERT_INTO_PUBLIC_ROLE_VALUES_DEFAULT1);
            for (Role role: objs) {
                preparedStatement.setString(1, role.getName().name());
                preparedStatement.setString(2, role.getDescription());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            List<Role> roles = new ArrayList<>();
            for (Role r: this.getAll()) {
                for(Role s: objs) {
                    if(r.getName().equals(s.getName()) &&
                            r.getDescription().equals(s.getDescription())) {
                        roles.add(r);
                    }
                }
            }
            return roles;
        } catch (SQLException e) {
            logger.error("Exception in addAll(Role)", e);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean truncate() {
        logger.info("truncate");
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    TRUNCATE_TABLE_PUBLIC_ROLE_CASCADE);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
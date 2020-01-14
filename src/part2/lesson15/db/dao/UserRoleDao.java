package part2.lesson15.db.dao;

import part2.lesson15.db.ConnectionManager.ConnectionManager;
import part2.lesson15.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson15.db.pojo.Role;
import part2.lesson15.db.pojo.User;
import part2.lesson15.db.pojo.UserRole;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.text.html.Option;
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

    private static ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    @Override
    public UserRole add(UserRole ur) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO  public.\"USER_ROLE\" values (DEFAULT, ?, ?) RETURNING id");
            preparedStatement.setInt(1, ur.getUserId());
            preparedStatement.setInt(2, ur.getRoleId());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new UserRole(resultSet.getInt(1),
                    ur.getUserId(),
                    ur.getRoleId());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<UserRole> getById(Integer id) {
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
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserRole> updateById(UserRole ur) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE  public.\"USER_ROLE\" SET user_id=?, role_id=?" +
                            "WHERE id=?");
            preparedStatement.setInt(1, ur.getUserId());
            preparedStatement.setInt(2, ur.getRoleId());
            System.out.println(preparedStatement.executeUpdate());
            return this.getById(ur.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Integer id) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM  public.\"USER_ROLE\" WHERE id=?");
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<UserRole> getAll() {
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
            e.printStackTrace();
        }
        return urs;
    }

    @Override
    public boolean truncate() {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "truncate table  public.\"USER_ROLE\"");
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<UserRole> addAll(List<UserRole> objs) {
        throw new NotImplementedException();
    }

}

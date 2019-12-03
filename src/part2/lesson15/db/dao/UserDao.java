package part2.lesson15.db.dao;

import part2.lesson15.db.ConnectionManager.ConnectionManager;
import part2.lesson15.db.ConnectionManager.ConnectionManagerJdbcImpl;
import part2.lesson15.db.pojo.Role;
import part2.lesson15.db.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KhafizovAR on 03.12.2019.
 * @project Stc20JavaMiddle
 */
public class UserDao implements GenericDao<User> {

    private static ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();

    @Override
    public boolean add(User user) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO  public.\"USER\" values (DEFAULT, ?, ?, ?,?,?,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setObject(2, user.getBirthday());
            preparedStatement.setString(3, user.getLoginId());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public User getById(Integer id) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM public.\"USER\" WHERE id = ?");
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
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateById(User user) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE  public.\"USER\" SET name=?, birthday=?, login_ID=?, city=?, email=?, description=? " +
                            "WHERE id=?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setObject(2, user.getBirthday());
            preparedStatement.setString(3, user.getLoginId());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            System.out.println(preparedStatement.executeUpdate());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM  public.\"USER\" WHERE id=?");
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM  public.\"USER\"");
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
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean addAll(List<User> objs) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO  public.\"USER\" values (DEFAULT, ?, ?, ?,?,?,?)");
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
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean truncate() {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "truncate table  public.\"USER\" cascade");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
    public User selectByNameAndLoginId(String name, String loginId) {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM public.\"USER\" WHERE name = ? and \"login_ID\" = ?");
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
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addM(User user, Connection conn) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO  public.\"USER\" values (DEFAULT, ?, ?, ?,?,?,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setObject(2, user.getBirthday());
            preparedStatement.setString(3, user.getLoginId());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            System.out.println(preparedStatement.executeUpdate());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

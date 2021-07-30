package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static UserDao userDao;

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoJDBCImpl();
        }
        return userDao;
    }

    private UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement statement = Util.getConnection()
                .prepareStatement(
                               "CREATE TABLE users ("
                                 + "id bigint NOT NULL AUTO_INCREMENT,"
                                 + " name varchar(40),"
                                 + " lastName varchar(40),"
                                 + " age tinyint(3), "
                                 + "PRIMARY KEY (id));")) {
            statement.execute();
        } catch (SQLException ignore) {

        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = Util.getConnection()
                .prepareStatement("DROP TABLE users;")) {

            statement.executeUpdate();
        } catch (SQLException ignore) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = Util.getConnection()
                .prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);")) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

            System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (SQLException ignore) {

        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement statement = Util.getConnection()
                .prepareStatement("DELETE FROM users WHERE id = ?;")) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ignore) {

        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        try (PreparedStatement statement = Util.getConnection()
                .prepareStatement("SELECT * FROM users;")) {

            ResultSet result = statement.executeQuery();

            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setLastName(result.getString("lastName"));
                user.setAge(result.getByte("age"));
                list.add(user);

                System.out.println(user);
            }
        } catch (SQLException ignore) {

        }
        return list;
    }

    public void cleanUsersTable() {
        try (PreparedStatement statement = Util.getConnection()
                .prepareStatement("DELETE FROM users;")) {

            statement.executeUpdate();
        } catch (SQLException ignore) {

        }
    }
}
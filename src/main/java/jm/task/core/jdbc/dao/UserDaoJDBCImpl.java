package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static UserDaoJDBCImpl userDaoJDBCImpl;

    public static UserDaoJDBCImpl getUserDaoJDBCImpl() {
        if (userDaoJDBCImpl == null) {
            userDaoJDBCImpl = new UserDaoJDBCImpl();
        }
        return userDaoJDBCImpl;
    }

    private UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String create =
                "CREATE TABLE users ("
                        + "id bigint NOT NULL AUTO_INCREMENT,"
                        + " name varchar(40),"
                        + " lastName varchar(40),"
                        + " age tinyint(3), "
                        + "PRIMARY KEY (id));";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(create)) {
            statement.execute();
        } catch (SQLException ignore) {

        }
    }

    public void dropUsersTable() {
        String drop = "DROP TABLE users;";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(drop)) {
            statement.executeUpdate();
        } catch (SQLException ignore) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insert = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(insert)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();

            System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (SQLException ignore) {

        }
    }

    public void removeUserById(long id) {
        String remove = "DELETE FROM users WHERE id = ?;";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(remove)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException ignore) {

        }
    }

    public List<User> getAllUsers() {
        String selectAll = "SELECT * FROM users;";
        List<User> list = new ArrayList<>();

        try (PreparedStatement statement = Util.getConnection().prepareStatement(selectAll)) {
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
        String clean = "DELETE FROM users;";

        try (PreparedStatement statement = Util.getConnection().prepareStatement(clean)) {
            statement.executeUpdate();
        } catch (SQLException ignore) {

        }
    }
}
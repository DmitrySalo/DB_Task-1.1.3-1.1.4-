package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mytestdb";
    private static final String USER = "root";
    private static final String PASS = "D1i9m9a11991";

    private static Connection connection = null;
    private static SessionFactory sessionFactory = null;

    private Util() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USER);
                properties.put(Environment.PASS, PASS);
                properties.put(Environment.SHOW_SQL, true);
                properties.put(Environment.HBM2DDL_AUTO, "create-drop");

                sessionFactory = new Configuration()
                        .addAnnotatedClass(User.class)
                        .addProperties(properties)
                        .buildSessionFactory();
            } catch (HibernateException exception) {
                exception.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
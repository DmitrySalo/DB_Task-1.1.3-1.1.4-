package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static UserDao userDao;

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoHibernateImpl();
        }
        return userDao;
    }

    private UserDaoHibernateImpl() {
    }

    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(
                "CREATE TABLE IF NOT EXISTS users ("
                        + "id bigint NOT NULL AUTO_INCREMENT,"
                        + " name varchar(40),"
                        + " lastName varchar(40),"
                        + " age tinyint(3), "
                        + "PRIMARY KEY (id));").executeUpdate();

        transaction.commit();
    }

    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
        transaction.commit();
    }

    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();

        System.out.printf("User с именем – %s добавлен в базу данных%n", name);
    }

    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);

        if (user != null) {
            session.delete(user);
        }

        transaction.commit();
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        List<User> list = session.createQuery("from User").list();

        if (list != null && !list.isEmpty()) {
            list.forEach(System.out::println);
        }

        return list;
    }

    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
        transaction.commit();
    }
}
package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
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
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(
                            "CREATE TABLE IF NOT EXISTS users ("
                            + "id bigint NOT NULL AUTO_INCREMENT,"
                            + " name varchar(40),"
                            + " lastName varchar(40),"
                            + " age tinyint(3), "
                            + "PRIMARY KEY (id));").executeUpdate();

            transaction.commit();
        } catch (HibernateException ignore) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void dropUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();

        } catch (HibernateException ignore) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();

            System.out.printf("User с именем – %s добавлен в базу данных%n", name);
        } catch (HibernateException ignore) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            User user = session.get(User.class, id);

            if (user != null) {
                session.delete(user);
            }

            transaction.commit();
        } catch (HibernateException ignore) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<User> list = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            list = session.createQuery("from User").list();
        } catch (HibernateException ignore) {

        }

        if (list != null && !list.isEmpty()) {
            list.forEach(System.out::println);
        }

        return list;
    }

    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();

        } catch (HibernateException ignore) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
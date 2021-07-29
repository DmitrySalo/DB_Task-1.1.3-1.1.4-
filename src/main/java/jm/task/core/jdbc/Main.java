package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBCImpl = UserDaoJDBCImpl.getUserDaoJDBCImpl();

        userDaoJDBCImpl.createUsersTable();

        userDaoJDBCImpl.saveUser("Robert", "Paulson", (byte) 46);
        userDaoJDBCImpl.saveUser("Green", "Elephant", (byte) 69);
        userDaoJDBCImpl.saveUser("Robocop", "Terminator", (byte) 99);
        userDaoJDBCImpl.saveUser("Emperor", "of mankind", (byte) 11);

        userDaoJDBCImpl.getAllUsers();
        userDaoJDBCImpl.cleanUsersTable();
        userDaoJDBCImpl.dropUsersTable();
    }
}
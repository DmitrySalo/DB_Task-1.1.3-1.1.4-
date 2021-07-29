package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();

        service.createUsersTable();

        service.saveUser("Robert", "Paulson", (byte) 46);
        service.saveUser("Green", "Elephant", (byte) 69);
        service.saveUser("Robocop", "Terminator", (byte) 99);
        service.saveUser("Emperor", "of mankind", (byte) 11);

        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
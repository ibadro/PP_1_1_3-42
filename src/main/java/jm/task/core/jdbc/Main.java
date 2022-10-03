package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userServiceImpl = new UserServiceImpl();
        userServiceImpl.createUsersTable();
        userServiceImpl.saveUser("ilnur", "badro", (byte) 40);
        userServiceImpl.saveUser("Daniel", "badro", (byte) 12);
        userServiceImpl.saveUser("Damir", "badro", (byte) 8);
        userServiceImpl.saveUser("XXXX01", "badro", (byte) 40);
        userServiceImpl.saveUser("XXXX01", "badro", (byte) 40);
        userServiceImpl.saveUser("XXXX01", "badro", (byte) 40);
        userServiceImpl.saveUser("XXXX01", "badro", (byte) 40);
        userServiceImpl.saveUser("XXXX01", "badro", (byte) 40);
        userServiceImpl.saveUser("XXXX01", "badro", (byte) 40);
        userServiceImpl.removeUserById(4);
        userServiceImpl.getAllUsers();
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();
    }
}

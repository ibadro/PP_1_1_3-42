package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private  Connection connection = Util.getInstance().getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() { //создание таблицы
        String sql = " CREATE TABLE IF NOT EXISTS users " +
                " (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(10)," +
                "lastName VARCHAR(10)," +
                "age INT)";
        try{
            connection.prepareStatement(sql)
                .executeUpdate();
            System.out.println("Таблица была создана!");
        } catch (SQLException e) {
        }
    }

    //удаление таблицы, если в ней существуют записи
    public void dropUsersTable() {
        try {
                connection.prepareStatement("DROP TABLE IF EXISTS userTable").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы!");

        }
    }

    //Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement pstm = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении!");

        }
    }

    //Удаление User из таблицы ( по id )
    public void removeUserById(long id) {
        try {
            connection.prepareStatement("DELETE FROM users WHERE id=?");

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении!");

        }

    }

    //Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
          List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(Long.valueOf(resultSet.getString(1)));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при возвращении всех пользователей");
            e.getErrorCode();
        }

        return users;
    }

    public void cleanUsersTable() { //Очистка содержания таблицы
        try { connection.prepareStatement("DELETE FROM users").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении данных!");

        }
    }
}
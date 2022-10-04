package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    private static UserDaoJDBCImpl instance;

    public UserDaoJDBCImpl() {
    }

    public static UserDaoJDBCImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoJDBCImpl();
        }
        return instance;
    }

    public void createUsersTable() { //создание таблицы
        String sql = " CREATE TABLE IF NOT EXISTS users " +
                " (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(10)," +
                "lastName VARCHAR(10)," +
                "age INT)";
        try (
                Connection connection = Util.getInstance().getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //удаление таблицы, если в ней существуют записи
    public void dropUsersTable() {
        try (Connection connection = Util.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Добавление User в таблицу
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO" +
                " users (name, lastName, age)" +
                " VALUES (?, ?, ?)";
        try (Connection connection = Util.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Удаление User из таблицы ( по id )
    public void removeUserById(long id) {
        try (Connection connection = Util.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Получение всех User(ов) из таблицы
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, lastName, age FROM users")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(Long.valueOf(resultSet.getString(1)));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при выводе пользователей!");
            e.printStackTrace();

        }
        return users;
    }

    public void cleanUsersTable() { //Очистка содержания таблицы
        try (Connection connection = Util.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private static Connection connection;
    private static volatile Util instance;

    private Util() {

    }
    public static Util getInstance() {
        if (instance == null) {
            synchronized (Util.class) {
                if (instance == null) {
                    instance = new Util();
                }
            }
        }
        return instance;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static SessionFactory mySessionFactory;                                  // Hibernate

    public static SessionFactory getSessionFactory() {
        Environment envrmnt = null;
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        try {
            settings.put(envrmnt.URL, URL);
            settings.put(envrmnt.USER, USERNAME);
            settings.put(envrmnt.PASS, PASSWORD);
            settings.put(envrmnt.AUTOCOMMIT, "false");
            settings.put(envrmnt.DIALECT, "org.hibernate.dialect.MySQLDialect");
            settings.put(envrmnt.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceReg = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            mySessionFactory = configuration
                    .buildSessionFactory(serviceReg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return mySessionFactory;
    }

    public static void connectionClose() {
        try {
            connection.close();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }

    }
}


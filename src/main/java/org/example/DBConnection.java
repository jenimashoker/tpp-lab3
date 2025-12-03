package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    // УВАГА: Я поставив порт 5433, як на твоєму скріншоті
    private static final String URL = "jdbc:postgresql://localhost:5433/lab3db";
    private static final String USER = "postgres";
    // ВПИШИ СЮДИ ПАРОЛЬ, який ти вводив при встановленні Postgres
    private static final String PASS = "postgres"; 

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        return DriverManager.getConnection(URL, props);
    }
}
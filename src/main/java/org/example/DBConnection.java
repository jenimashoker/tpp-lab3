package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5433/lab3db";
    private static final String USER = "postgres";
    private static final String PASS = "postgres"; 

    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PASS);
        return DriverManager.getConnection(URL, props);
    }
}
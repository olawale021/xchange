package com.example;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());
    private static final Dotenv dotenv = Dotenv.configure().load();

    private static final String jdbcUrl = dotenv.get("DB_URL");
    private static final String username = dotenv.get("DB_USERNAME");
    private static final String password = dotenv.get("DB_PASSWORD");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            LOGGER.info("MySQL JDBC Driver loaded successfully.");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC driver not found", e);
            throw new ExceptionInInitializerError("MySQL JDBC driver not found");
        }
    }

    public static Connection getConnection() throws SQLException {
        if (jdbcUrl == null || username == null || password == null) {
            throw new SQLException("Database connection details are not set. Please check your .env file.");
        }
        LOGGER.info("JDBC URL: " + jdbcUrl);
        LOGGER.info("DB Username: " + username);
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            LOGGER.info("Connected to the database!");
            connection.close();  // Close the connection after use
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to connect to the database.", e);
        }
    }
}
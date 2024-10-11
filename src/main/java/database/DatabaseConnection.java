package database;

import Konstanten.Constants;
import interfaces.IDatabaseConnection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnection implements IDatabaseConnection {

    private Connection connection = null;


    @Override
    public IDatabaseConnection openConnection(Properties properties) {
        String systemnutzer = System.getProperty("user.name");
        try {
            String dbUrl = properties.getProperty(systemnutzer + ".db.url");
            String dbUser = properties.getProperty(systemnutzer + ".db.user");
            String dbPassword = properties.getProperty(systemnutzer + ".db.pw");

            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void createAllTables() {
        if (this.connection == null) {
            throw new RuntimeException("Database Connection is null");
        }

        try {
            Statement statement = this.connection.createStatement();
            statement.execute("""
                    CREATE OR REPLACE TABLE customers
                    (
                        id        UUID                      NOT NULL DEFAULT (UUID()),
                        gender    ENUM ('D', 'M', 'U', 'W') NOT NULL,
                        firstname VARCHAR(50),
                        lastname  VARCHAR(50)               NOT NULL,
                        birthdate DATE,
                        PRIMARY KEY (id)
                    );
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void truncateAllTables() {

    }

    @Override
    public void removeAllTables() {

    }

    @Override
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

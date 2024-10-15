package database;

import interfaces.IDatabaseConnection;

import java.sql.*;
import java.util.Properties;

public class DatabaseConnection implements IDatabaseConnection {

    private static Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    @Override
    public IDatabaseConnection openConnection(Properties properties) {
        if (connection == null) {
            try {
                String systemUser = System.getProperty("user.name");
                String dbUrl = properties.getProperty(systemUser + ".db.url");
                String dbUser = properties.getProperty(systemUser + ".db.user");
                String dbPassword = properties.getProperty(systemUser + ".db.pw");

                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return this;
    }

    @Override
    public void createAllTables() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS readings;");
            statement.execute("DROP TABLE IF EXISTS customers;");
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

            statement.execute("""
                    CREATE OR REPLACE TABLE readings
                    (
                        id          UUID                                          NOT NULL DEFAULT (UUID()),
                        customer_id UUID,
                        date        DATE                                          NOT NULL,
                        meter_ID    VARCHAR(100)                                  NOT NULL,
                        value       DOUBLE UNSIGNED                               NOT NULL,
                        meter_type  ENUM ('HEIZUNG','STROM','WASSER','UNBEKANNT') NOT NULL,
                        comment     VARCHAR(100),
                        PRIMARY KEY (id),
                        CONSTRAINT FK_CustomerReading
                            FOREIGN KEY (customer_id) REFERENCES customers (id)
                                ON UPDATE CASCADE
                                ON DELETE SET NULL
                    );
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void truncateAllTables() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("ALTER TABLE readings DROP CONSTRAINT FK_CustomerReading;");
            statement.execute("TRUNCATE TABLE readings;");
            statement.execute("TRUNCATE TABLE customers;");
            statement.execute("""
                    ALTER TABLE readings
                        ADD CONSTRAINT FK_CustomerReading FOREIGN KEY (customer_id) REFERENCES customers (id);
                    """);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeAllTables() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS readings;");
            statement.execute("DROP TABLE IF EXISTS customers;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        IDatabaseConnection connection = new DatabaseConnection().openConnection(Property.readProperties());
        connection.createAllTables();
        connection.truncateAllTables();
    }
}

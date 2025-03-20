package database;

import interfaces.IDatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseConnection implements IDatabaseConnection {    //Nötig für die Datenbankverbindung

    private static Connection connection = null;

    public Connection getConnection() {
        return connection;
    }   //Gibt die Aktuelle Verbindung zurück

    @Override
    public IDatabaseConnection openConnection(Properties properties) {  //Öffnet die verbindung, zur Datenbank
        if (connection == null) {
            try {
                String systemUser = System.getProperty("user.name");
                String dbUrl = properties.getProperty(systemUser + ".db.url");
                String dbUser = properties.getProperty(systemUser + ".db.user");
                String dbPassword = properties.getProperty(systemUser + ".db.pw");

                connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

        return this;
    }

    @Override
    public void createAllTables() { //Erzeugt alle Tabellen
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS readings;");
            statement.execute("DROP TABLE IF EXISTS customers;");
            statement.execute("DROP TABLE IF EXISTS users;");
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
                        meter_count DOUBLE UNSIGNED                               NOT NULL,
                        meter_type  ENUM ('HEIZUNG','STROM','WASSER','UNBEKANNT') NOT NULL,
                        comment     VARCHAR(100),
                        substitute  BOOLEAN,
                        PRIMARY KEY (id),
                        CONSTRAINT FK_CustomerReading
                            FOREIGN KEY (customer_id) REFERENCES customers (id)
                                ON UPDATE CASCADE
                                ON DELETE SET NULL
                    );
                    """);
            statement.execute("""
                    CREATE OR REPLACE TABLE users (
                        id          UUID                        NOT NULL DEFAULT(UUID()),
                        username    VARCHAR(100)                NOT NULL UNIQUE,
                        password    VARCHAR(200)                NOT NULL,
                        role        VarChar(15)                 NOT NULL,
                        customer_id  UUID,
                        CONSTRAINT FK_CustomerReading
                            FOREIGN KEY (customer_id) REFERENCES customers (id)
                                ON UPDATE CASCADE
                                ON DELETE SET NULL
                        );
                    """);

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void truncateAllTables() {   //Entfernt Leere Einträge
        try {
            Statement statement = connection.createStatement();
            statement.execute("ALTER TABLE readings DROP CONSTRAINT FK_CustomerReading;");
            statement.execute("TRUNCATE TABLE readings;");
            statement.execute("TRUNCATE TABLE customers;");
            statement.execute("TRUNCATE TABLE users;");
            statement.execute("""
                    ALTER TABLE readings
                        ADD CONSTRAINT FK_CustomerReading FOREIGN KEY (customer_id) REFERENCES customers (id);
                    """);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void removeAllTables() { //Löscht alle Tabellen
        try {
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS readings;");
            statement.execute("DROP TABLE IF EXISTS customers;");
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void closeConnection() { //Schließt die Verbindung
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

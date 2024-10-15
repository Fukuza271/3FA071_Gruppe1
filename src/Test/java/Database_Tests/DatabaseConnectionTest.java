package Database_Tests;

import database.DatabaseConnection;
import database.Property;
import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DatabaseConnectionTest {

    IDatabaseConnection connection;

    @Test
    public void testOpenConnection() throws SQLException {
        connection = new DatabaseConnection();
        IDatabaseConnection result = connection.openConnection(Property.readProperties());
        Assertions.assertNotNull(result);
    }

    @AfterAll
    public void closeConnectionTest(){
        connection.closeConnection();
    }
}

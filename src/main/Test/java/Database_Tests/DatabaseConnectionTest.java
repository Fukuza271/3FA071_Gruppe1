package Database_Tests;

import database.DatabaseConnection;
import database.Property;
import interfaces.IDatabaseConnection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
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
        Assert.assertNotNull(result);
    }

    @After
    public void closeConnectionTest(){
        connection.closeConnection();
    }
}

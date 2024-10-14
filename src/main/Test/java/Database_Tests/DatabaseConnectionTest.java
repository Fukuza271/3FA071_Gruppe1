package Database_Tests;

import database.DatabaseConnection;
import database.Property;
import interfaces.IDatabaseConnection;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnectionTest {

    @Test
    public void testOpenConnection() throws SQLException {
        IDatabaseConnection connection = new DatabaseConnection();
        IDatabaseConnection result = connection.openConnection(Property.readProperties());
        Assert.assertNotNull(result);
    }
}

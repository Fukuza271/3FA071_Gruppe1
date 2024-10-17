package Database_Tests;

import database.DatabaseConnection;
import database.Property;
import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.*;

import java.util.Properties;

@Order(3)
public class PropertyTest {

    @BeforeAll
    public static void setUp() {
        System.setProperty("user.name", "TestUser");
    }

    @Test
    public void testProperty() {
        Properties properties = Property.readProperties();
        Assertions.assertEquals(properties.getProperty("TestUser.db.url"), "jdbc:mariadb://localhost:3306/HausFix");
        Assertions.assertEquals(properties.getProperty("TestUser.db.user"), "root");
        Assertions.assertEquals(properties.getProperty("TestUser.db.pw"), "root");
    }

    @AfterAll
    public static void end() {
        IDatabaseConnection connection = new DatabaseConnection();
        connection.closeConnection();
    }
}

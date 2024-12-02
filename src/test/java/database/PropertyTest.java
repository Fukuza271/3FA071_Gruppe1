package database;

import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.*;

import java.util.Properties;

public class PropertyTest extends DatabaseTest {

    @Test
    public void readPropertiesTest() {
        Properties properties = Property.readTestProperties();

        Assertions.assertEquals(properties.getProperty("TestUser.db.url"), "jdbc:mariadb://localhost:3306/HausFix");
        Assertions.assertEquals(properties.getProperty("TestUser.db.user"), "root");
        Assertions.assertEquals(properties.getProperty("TestUser.db.pw"), "root");
    }

}

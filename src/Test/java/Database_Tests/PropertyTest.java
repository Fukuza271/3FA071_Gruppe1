package Database_Tests;

import database.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Properties;

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
}

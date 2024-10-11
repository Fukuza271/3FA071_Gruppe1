package Database_Tests;

import database.Property;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

public class PropertyTest {

    @Before
    public void setUp() {
        System.setProperty("user.name", "TestUser");
    }

    @Test
    public void testProperty() {
        Properties properties = Property.readProperties();
        Assert.assertEquals(properties.getProperty("TestUser.db.url"), "jdbc:mariadb://localhost:3306/HausFix");
        Assert.assertEquals(properties.getProperty("TestUser.db.user"), "root");
        Assert.assertEquals(properties.getProperty("TestUser.db.pw"), "root");
    }
}

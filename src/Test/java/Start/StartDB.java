package Start;

import database.DatabaseConnection;
import database.Property;
import org.junit.jupiter.api.Test;
import java.util.Properties;

public class StartDB {

    @Test
    public void testDb() {
        Properties properties = Property.readProperties();
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection(properties);
        connection.createAllTables();
        connection.truncateAllTables();
        connection.removeAllTables();
    }
}

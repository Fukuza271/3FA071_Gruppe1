import database.DatabaseConnection;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Properties properties = Property.readProperties();
        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection(properties);
        connection.createAllTables();

    }
}

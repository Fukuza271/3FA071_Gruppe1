package database;

import database.entities.Customer;
import interfaces.ICustomer;
import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class DatabaseConnectionTest extends DatabaseTest {

    @Test
    public void openConnectionTest() {
        connection = new DatabaseConnection();
        IDatabaseConnection result = connection.openConnection(Property.readProperties());
        Assertions.assertNotNull(result);
    }

    @Test
    public void truncateAllTablesTest() {
        customerDao.insert(new Customer(UUID.randomUUID(), ICustomer.Gender.U, "", "", null));
        Assertions.assertEquals(customerDao.findAll().size(), 1);

        connection.truncateAllTables();

        Assertions.assertEquals(customerDao.findAll().size(), 0);
    }

    @Test
    public void removeAllTablesTest() {
        connection.createAllTables();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.removeAllTables();
    }
}

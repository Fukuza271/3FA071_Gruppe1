package database;

import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class DatabaseConnectionTest extends DatabaseTest {

    @Test
    public void testOpenConnection() {
        connection = new DatabaseConnection();
        IDatabaseConnection result = connection.openConnection(Property.readProperties());
        Assertions.assertNotNull(result);
    }

//    @Test
//    public void truncateAllTablesTest() {
//
//        TestData.insertCustomerTestData();
//        System.out.println("Listsize: " + customerDao.findAll().size());
//        Assertions.assertEquals(customerDao.findAll().size(), 10);
//
//        connection.truncateAllTables();
//        System.out.println("Listsize: " + customerDao.findAll().size());
//        Assertions.assertEquals(customerDao.findAll().size(), 0);
//    }

    @Test
    public void removeAllTablesTest() {
        connection.createAllTables();
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.removeAllTables();
    }
}

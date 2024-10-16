package Database_Tests;

import database.DatabaseConnection;
import database.Property;
import database.daos.CustomerDao;
import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
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
        Assertions.assertNotNull(result);
        connection.closeConnection();
    }




    @Test
    public void truncateAllTablesTest() {
        DatabaseConnection dbCon = new DatabaseConnection();
        CustomerDao custDao = new CustomerDao();

        TestData.insertCustomerTestData();
        System.out.println("Listsize: " + custDao.findAll().size());
        Assertions.assertEquals(custDao.findAll().size(), 10);

        dbCon.truncateAllTables();
        System.out.println("Listsize: " + custDao.findAll().size());
        Assertions.assertEquals(custDao.findAll().size(), 0);


    }
}

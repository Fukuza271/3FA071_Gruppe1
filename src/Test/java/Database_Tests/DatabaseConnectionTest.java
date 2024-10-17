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


public class DatabaseConnectionTest extends BasicTests{

    IDatabaseConnection connection;

    @Test
    public void testOpenConnection() throws SQLException {
        connection = new DatabaseConnection();
        IDatabaseConnection result = connection.openConnection(Property.readProperties());
        Assertions.assertNotNull(result);
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



    @Test
    public void removeAllTablesTest() {
        DatabaseConnection dbCon = new DatabaseConnection();
        CustomerDao custDao = new CustomerDao();

        try {
            Statement statement = connection.getConnection().createStatement();

            statement.execute("DROP TABLE IF EXISTS readings;");
            statement.execute("DROP TABLE IF EXISTS customers;");



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



//    @AfterAll
//    public static void cleanUp(){
//        IDatabaseConnection connection = new DatabaseConnection();
//        connection.closeConnection();
//    }
}

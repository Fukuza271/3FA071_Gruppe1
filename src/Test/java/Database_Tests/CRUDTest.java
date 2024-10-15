package Database_Tests;

import database.DatabaseConnection;
import database.daos.CustomerDao;
import database.entities.Customer;
import interfaces.ICustomer;
import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.UUID;

public class CRUDTest {

    @BeforeEach
    public void setUp() {
        TestData.insertCustomerTestData();
    }

    @Test
    public void findByIdTest() {
        CustomerDao customerDao = new CustomerDao();

        // Beliebige UUID aus der Testdatenbank genommen
        Customer customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertNotNull(customer);
        customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db2"));
        Assertions.assertNull(customer);
    }

    @Test
    public void insertTest() {
        Customer testCustomer = new Customer(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db7")
                , ICustomer.Gender.M
                , "Thomas"
                , "Bauer"
                , LocalDate.of(1989, 12, 03));
        CustomerDao customerDao = new CustomerDao();
        Assertions.assertNull(customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db7")));
    }

    @Test
    public void updateTest() {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        customer.setLastName("Bauer");
        customerDao.update(customer);
        // Denselben Kunden aus der Datenbank holen und den geupdateten Wert ablesen
        Customer vergleichsCustomer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertEquals(vergleichsCustomer.getLastName(), "Bauer");
    }

    @Test
    public void deleteTest() {
        CustomerDao customerDao = new CustomerDao();
        customerDao.deleteById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertNull(customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3")));
    }

    @AfterAll
    public static void closeConnection() {
        IDatabaseConnection connection = new DatabaseConnection();
        connection.closeConnection();
    }
}

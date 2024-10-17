package Database_Tests;

import database.DatabaseConnection;
import database.daos.CustomerDao;
import database.daos.ReadingDao;
import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;
import interfaces.IDatabaseConnection;
import interfaces.IReading;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CRUDTest extends BasicTests{

    @BeforeEach
    public void setUp() {
        TestData.insertCustomerTestData();
    }

    // Customer Dao Tests

    @Test
    public void findByIdCustomerTest() {
        CustomerDao customerDao = new CustomerDao();

        // Beliebige UUID aus der Testdatenbank genommen
        Customer customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertNotNull(customer);
        customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db2"));
        Assertions.assertNull(customer);
    }

    @Test
    public void insertCustomerTest() {
        Customer testCustomer = new Customer(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db7")
                , ICustomer.Gender.M
                , "Thomas"
                , "Bauer"
                , LocalDate.of(1989, 12, 03));
        CustomerDao customerDao = new CustomerDao();
        customerDao.insert(testCustomer);
        Assertions.assertNotNull(customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db7")));
    }

    @Test
    public void updateCustomerTest() {
        CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        customer.setLastName("Bauer");
        customerDao.update(customer);
        // Denselben Kunden aus der Datenbank holen und den geupdateten Wert ablesen
        Customer vergleichsCustomer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertEquals(vergleichsCustomer.getLastName(), "Bauer");
    }

    @Test
    public void deleteCustomerTest() {
        CustomerDao customerDao = new CustomerDao();
        customerDao.deleteById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertNull(customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3")));
    }

    @Test
    public void findAllCustomerTest() {
        CustomerDao custDao = new CustomerDao();
        System.out.println("Listsize: " + custDao.findAll().size());
        Assertions.assertEquals(custDao.findAll().size(), 10);
    }

    // Reading Dao Tests

    @Test
    public void findByIdReadingTest() {
        ReadingDao readingDao = new ReadingDao();
        Reading reading = readingDao.findById(UUID.fromString(""));
        Assertions.assertNotNull(reading);
        Reading reading2 = readingDao.findById(UUID.fromString(""));
        Assertions.assertNull(reading2);
    }

    @Test
    public void insertUpdateTest() {
        ReadingDao readingDao = new ReadingDao();
        Reading reading = readingDao.findById(UUID.fromString(""));
        reading.setSubstitute(true);
        readingDao.update(reading);
        Assertions.assertTrue(reading.getSubstitute());
    }

    @Test
    public void deleteReadingTest() {
        ReadingDao readingDao = new ReadingDao();
        readingDao.deleteById(UUID.fromString(""));
        Assertions.assertNull(readingDao.findById(UUID.fromString("")));
    }

    @Test
    public void updateReadingTest() {
        ReadingDao readingDao = new ReadingDao();
        Reading reading = new Reading(
                UUID.fromString(""),
                UUID.fromString(""),
                LocalDate.now(),
                "",
                2.d,
                IReading.KindOfMeter.HEIZUNG,
                "");
        readingDao.insert(reading);
        Assertions.assertNotNull(readingDao.findById(UUID.fromString("")));
    }

    @Test
    public void findAllReadingTest() {
        ReadingDao readingDao = new ReadingDao();
        List<Reading> readings = readingDao.findAll();
        Assertions.assertEquals(readings.size(), 10);
    }

//    @AfterAll
//    public static void closeConnection() {
//        IDatabaseConnection connection = new DatabaseConnection();
//        connection.closeConnection();
//    }
}

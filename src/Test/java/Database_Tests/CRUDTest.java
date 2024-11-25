package Database_Tests;

import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;
import interfaces.IReading;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Order(2)
public class CRUDTest extends BasicTests {

    @BeforeAll
    public static void set() {
        connection.createAllTables();
        // Readings Testuser mit unveränderbarer UUID
        readingDao.insert(new Reading(
                UUID.fromString("c7525a6a-31fb-4baf-8ff5-e6d31fa3c1e1"),
                UUID.fromString("848c39a1-0cbb-427a-ac6f-a88941943dc8"),
                LocalDate.now(),
                "Xr-2018-2312456ab",
                1.918,
                IReading.KindOfMeter.HEIZUNG,
                "",
                false)
        );
    }

    @BeforeEach
    public void setUp() {
        connection.createAllTables();
        TestData.insertCustomerData();
        TestData.insertHeizungReadingData();
        TestData.insertStromReadingData();
        TestData.insertWasserReadingData();
    }

    // Customer Dao Tests

    @Test
    public void findByIdCustomerTest() {
        // Beliebige UUID aus der Testdatenbank genommen
        Customer customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertNotNull(customer);
        customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db2"));
        // Max Mustermann erwartet
        Assertions.assertNotNull(customer);
    }

    @Test
    public void insertCustomerTest() {
        Customer testCustomer = new Customer(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db7")
                , ICustomer.Gender.M
                , "Thomas"
                , "Bauer"
                , LocalDate.of(1989, 12, 03));
        customerDao.insert(testCustomer);
        Assertions.assertNotNull(customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db7")));
    }

    @Test
    public void updateCustomerTest() {
        Customer customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        customer.setLastName("Bauer");
        customerDao.update(customer);
        // Denselben Kunden aus der Datenbank holen und den geupdateten Wert ablesen
        Customer vergleichsCustomer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertEquals(vergleichsCustomer.getLastName(), "Bauer");
    }

    @Test
    public void deleteCustomerTest() {
        customerDao.deleteById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertNotNull(customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3")));
    }

    @Test
    public void findAllCustomerTest() {
        System.out.println("Listsize: " + customerDao.findAll().size());
        Assertions.assertEquals(customerDao.findAll().size(), 10);
    }

    @Test
    public void whereCustomerTest() {
        List<Customer> customer = customerDao.where("lastName", "=", "Jäger");
        Assertions.assertEquals(customer.getFirst().getLastName(), "Jäger");
        Assertions.assertEquals(customer.size(), 1);
    }

    // Reading Dao Tests

    @Test
    public void findByIdReadingTest() {
        Reading reading = readingDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertNotNull(reading);
        Reading reading2 = readingDao.findById(UUID.fromString("c7525a6a-31fb-4baf-8ff5-e6d31fa3c1e1"));
        Assertions.assertNull(reading2);
    }

    @Test
    public void updateReadingTest() {
        Reading reading = readingDao.findById(UUID.fromString("c7525a6a-31fb-4baf-8ff5-e6d31fa3c1e1"));
        reading.setSubstitute(true);
        readingDao.update(reading);
        Assertions.assertTrue(reading.getSubstitute());
    }

    @Test
    public void deleteReadingTest() {
        readingDao.deleteById(UUID.fromString("c7525a6a-31fb-4baf-8ff5-e6d31fa3c1e1"));
        Assertions.assertNull(readingDao.findById(UUID.fromString("c7525a6a-31fb-4baf-8ff5-e6d31fa3c1e1")));
    }

    @Test
    public void insertReadingTest() {
        Reading reading = new Reading(
                UUID.fromString("c7525a6a-31fb-4baf-8ff5-e6d31fa3c1e1"),
                UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"),
                LocalDate.now(),
                "",
                2.d,
                IReading.KindOfMeter.HEIZUNG,
                "",
                false);
        readingDao.insert(reading);
        Assertions.assertNotNull(readingDao.findById(UUID.fromString("c7525a6a-31fb-4baf-8ff5-e6d31fa3c1e1")));
    }

    @Test
    public void findAllReadingTest() {
        List<Reading> readings = readingDao.findAll();
        Assertions.assertEquals(0, readings.size());
    }

    @Test
    public void whereReadingTest() {
        List<Reading> readings = readingDao.where("meter_count", "=", "1.918");
        Assertions.assertEquals(readings.getFirst().getMeterCount(), "1.918");
        Assertions.assertEquals(readings.size(), 1);
    }
}
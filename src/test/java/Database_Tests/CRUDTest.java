package Database_Tests;

import database.ExampleDataProvider;
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

//    @BeforeAll
//    public static void set() {
//        connection.createAllTables();
//
//    }

    @BeforeEach
    public void setUp() {
        connection.createAllTables();

        ExampleDataProvider.shared.insertCustomerData();
        ExampleDataProvider.shared.insertHeizungReadingData();
        ExampleDataProvider.shared.insertStromReadingData();
        ExampleDataProvider.shared.insertWasserReadingData();
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
        Assertions.assertEquals(customerDao.findAll().size(), 1001);
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
        Reading reading;
        readingDao.insert(new Reading(
                UUID.fromString("c7525a6a-31fb-4baf-8ff5-e6d31fa3c1e1"),
                UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"),
                LocalDate.now(),
                "",
                2.d,
                IReading.KindOfMeter.HEIZUNG,
                "",
                false));
        reading = readingDao.findById(UUID.fromString("c7525a6a-31fb-4baf-8ff5-e6d31fa3c1e1"));
        Assertions.assertNotNull(reading);
    }

    @Test
    public void updateReadingTest() {
        Reading reading = readingDao.findAll().getFirst();
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
        Assertions.assertEquals(205, readings.size());
    }

    @Test
    public void whereReadingTest() {
        List<Reading> readings = readingDao.where("meter_count", "=", "1.918");
        Assertions.assertEquals(readings.getFirst().getMeterCount().toString(), "1.918");
        Assertions.assertEquals(readings.size(), 1);
    }
}
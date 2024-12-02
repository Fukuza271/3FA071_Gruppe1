package database;

import database.entities.Customer;
import interfaces.ICustomer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class ExampleDataProviderTest extends DatabaseTest {

    String testCustomerUUID = "ec617965-88b4-4721-8158-ee36c38e4db3";

    private void insertTestCustomer() {
        customerDao.insert(new Customer(UUID.fromString(testCustomerUUID),
                ICustomer.Gender.M, "Pumukel", "Kobold", LocalDate.now()));
    }

    @Test
    public void insertHeizungReadingDataTest() {
        Assertions.assertEquals(0, readingDao.findAll().size());

        insertTestCustomer();

        ExampleDataProvider.shared.insertHeizungReadingData();

        Assertions.assertEquals(75, readingDao.findAll().size());
    }

    @Test
    public void insertWasserReadingDataTest() {
        Assertions.assertEquals(0, readingDao.findAll().size());

        insertTestCustomer();

        ExampleDataProvider.shared.insertWasserReadingData();

        Assertions.assertEquals(66, readingDao.findAll().size());
    }

    @Test
    public void insertStromReadingDataTest() {
        Assertions.assertEquals(0, readingDao.findAll().size());

        insertTestCustomer();

        ExampleDataProvider.shared.insertStromReadingData();

        Assertions.assertEquals(64, readingDao.findAll().size());
    }

    @Test
    public void insertCustomerData() {
        Assertions.assertEquals(0, customerDao.findAll().size());

        ExampleDataProvider.shared.insertCustomerData();

        Assertions.assertEquals(1001, customerDao.findAll().size());
    }

    @Test
    public void errors() {
        System.setProperty("user.name", "TestUser");

        ExampleDataProvider.shared.insertCustomerData();
    }

}

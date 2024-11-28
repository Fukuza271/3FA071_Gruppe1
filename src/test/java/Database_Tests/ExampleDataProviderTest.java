package Database_Tests;

import database.ExampleDataProvider;
import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;
import interfaces.IReading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class ExampleDataProviderTest extends BasicTests {

    @BeforeEach
    public void setUp(){
        BasicTests.connection.removeAllTables();
        BasicTests.connection.createAllTables();
    }

    String testCustomerUUID = "ec617965-88b4-4721-8158-ee36c38e4db3";

    public void insertTestCustomer() {
        customerDao.insert(new Customer(UUID.fromString(testCustomerUUID),
                ICustomer.Gender.M, "Pumukel", "Kobold", LocalDate.now()));
    }

    @Test
    public void insertHeizungReadingDataTest() {

        Assertions.assertEquals(0, readingDao.findAll().size());

        insertTestCustomer();

        readingDao.insert(new Reading(UUID.randomUUID(),
                UUID.fromString(testCustomerUUID), LocalDate.now(),
                "H-2010-2312456ab", 5.965, IReading.KindOfMeter.HEIZUNG,
                "TestReading", false));

        Assertions.assertEquals(1, readingDao.findAll().size());
    }

    @Test
    public void insertWasserReadingDataTest() {

        Assertions.assertEquals(0, readingDao.findAll().size());

        insertTestCustomer();

        readingDao.insert(new Reading(UUID.randomUUID(),
                UUID.fromString(testCustomerUUID), LocalDate.now(),
                "S-2011-2312456cd", 16573.d, IReading.KindOfMeter.HEIZUNG,
                "TestReading", false));

        Assertions.assertEquals(1, readingDao.findAll().size());
    }

    @Test
    public void insertStromReadingDataTest() {

        Assertions.assertEquals(0, readingDao.findAll().size());

        insertTestCustomer();

        readingDao.insert(new Reading(UUID.randomUUID(),
                UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"), LocalDate.now(),
                "W-2012-2312456ef", 710.d, IReading.KindOfMeter.HEIZUNG,
                "TestReading", false));

        Assertions.assertEquals(1, readingDao.findAll().size());
    }

    @Test
    public void insertCustomerData() {

        Assertions.assertEquals(0, customerDao.findAll().size());

        ExampleDataProvider.shared.insertCustomerData();

        Assertions.assertEquals(1001, customerDao.findAll().size());
    }

}

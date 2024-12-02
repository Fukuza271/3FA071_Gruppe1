package database;

import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;
import interfaces.IReading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReadingDaoTest extends DatabaseTest {

    private Customer createCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), ICustomer.Gender.W, "Jane", "Doe", LocalDate.now());
        customerDao.insert(customer);

        return customer;
    }

    private Reading createReading() {
        Reading reading = new Reading(UUID.randomUUID(), createCustomer().getId(), LocalDate.now(), "", 5.0, IReading.KindOfMeter.STROM, "", false);
        readingDao.insert(reading);

        return reading;
    }

    @Test
    public void findByIdTest() {
        Reading reading = createReading();
        Assertions.assertNotNull(readingDao.findById(reading.getId()));
    }

    @Test
    public void insertTest() {
        Reading reading = new Reading(UUID.randomUUID(), createCustomer().getId(), LocalDate.now(), "", 2.0, IReading.KindOfMeter.HEIZUNG, "", false);

        readingDao.insert(reading);

        Assertions.assertNotNull(readingDao.findById(reading.getId()));
    }

    @Test
    public void updateTest() {
        Reading reading = createReading();

        Assertions.assertFalse(readingDao.findById(reading.getId()).getSubstitute());

        reading.setSubstitute(true);
        readingDao.update(reading);

        Assertions.assertTrue(readingDao.findById(reading.getId()).getSubstitute());
    }

    @Test
    public void deleteTest() {
        Reading reading = createReading();

        Assertions.assertNotNull(readingDao.findById(reading.getId()));

        readingDao.deleteById(reading.getId());

        Assertions.assertNull(readingDao.findById(reading.getId()));
    }

    @Test
    public void findAllTest() {
        Assertions.assertEquals(0, readingDao.findAll().size());

        createReading();
        createReading();
        createReading();

        Assertions.assertEquals(3, readingDao.findAll().size());
    }

    @Test
    public void whereReadingTest() {
        Reading reading = createReading();
        createReading();

        List<Reading> readings = readingDao.where("id", "=", reading.getId().toString());
        Assertions.assertEquals(readings.getFirst().getId().toString(), reading.getId().toString());
        Assertions.assertEquals(readings.size(), 1);
    }
}

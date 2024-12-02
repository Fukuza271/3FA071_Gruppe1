package database.daos;

import database.DatabaseTest;
import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;
import interfaces.IReading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
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
        List<Condition> conditions = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Customer customer = new Customer(uuid, ICustomer.Gender.M, "Schnieder", "Schnieder", LocalDate.now());
        customerDao.insert(customer);
        Reading reading = new Reading(UUID.randomUUID(),
                uuid,
                LocalDate.now(),
                "",
                1.918,
                IReading.KindOfMeter.WASSER,
                "",
                false);
        Reading reading2 = new Reading(UUID.randomUUID(),
                uuid,
                LocalDate.now(),
                "",
                0.918,
                IReading.KindOfMeter.WASSER,
                "",
                false);
        Reading reading3 = new Reading(UUID.randomUUID(),
                uuid,
                LocalDate.now(),
                "",
                1.919,
                IReading.KindOfMeter.WASSER,
                "",
                false);
        readingDao.insert(reading);
        readingDao.insert(reading2);
        readingDao.insert(reading3);
        conditions.add(new Condition("meter_count", "=", "1.918", "OR"));
        conditions.add(new Condition("meter_count", "<", "1.918", null));
        List<Reading> readings = readingDao.where(conditions);
        Assertions.assertEquals(2, readings.size());
    }
}

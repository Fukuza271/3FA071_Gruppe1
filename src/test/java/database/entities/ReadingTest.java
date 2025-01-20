package database.entities;

import interfaces.ICustomer;
import interfaces.IReading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class ReadingTest extends EntityTest {
    @Test
    public void getCommentTest() {
        Assertions.assertEquals("comment", this.reading.getComment());
    }

    @Test
    public void getCustomerTest() {
        Assertions.assertEquals(this.customer, this.reading.getCustomer());
    }

    @Test
    public void getNullCustomerTest() {
        customerDao.deleteById(this.customer.getId());
        Assertions.assertNull(this.reading.getCustomer());
    }

    @Test
    public void getDateOfReadingTest() {
        Assertions.assertEquals(LocalDate.of(2024, 12, 6), this.reading.getDateOfReading());
    }

    @Test
    public void getKindOfMeterTest() {
        Assertions.assertEquals(IReading.KindOfMeter.WASSER, this.reading.getKindOfMeter());
    }

    @Test
    public void getMeterCountTest() {
        Assertions.assertEquals(123, this.reading.getMeterCount());
    }

    @Test
    public void getMeterIdTest() {
        Assertions.assertEquals("123", this.reading.getMeterId());
    }

    @Test
    public void getSubstituteTest() {
        Assertions.assertFalse(this.reading.getSubstitute());
    }

    @Test
    public void printDateOfReadingTest() {
        Assertions.assertEquals("2024-12-06", this.reading.printDateOfReading());
    }

    @Test
    public void setCommentTest() {
        Assertions.assertNotEquals("new comment", this.reading.getComment());

        this.reading.setComment("new comment");

        Assertions.assertEquals("new comment", this.reading.getComment());
    }

    @Test
    public void setCustomerTest() {
        Customer newCustomer = new Customer(
                UUID.randomUUID(),
                ICustomer.Gender.D,
                "New",
                "Customer",
                LocalDate.now()
        );
        customerDao.insert(newCustomer);

        Assertions.assertNotEquals(newCustomer, this.reading.getCustomer());

        this.reading.setCustomer(newCustomer);

        Assertions.assertEquals(newCustomer, this.reading.getCustomer());
    }

    @Test
    public void setDateOfReadingTest() {
        LocalDate today = LocalDate.now();

        Assertions.assertNotEquals(today, this.reading.getDateOfReading());

        this.reading.setDateOfReading(today);

        Assertions.assertEquals(today, this.reading.getDateOfReading());
    }

    @Test
    public void setKindOfMeterTest() {
        Assertions.assertNotEquals(IReading.KindOfMeter.STROM, this.reading.getKindOfMeter());

        this.reading.setKindOfMeter(IReading.KindOfMeter.STROM);

        Assertions.assertEquals(IReading.KindOfMeter.STROM, this.reading.getKindOfMeter());
    }

    @Test
    public void setMeterCountTest() {
        Assertions.assertNotEquals(321.0, this.reading.getMeterCount());

        this.reading.setMeterCount(321.0);

        Assertions.assertEquals(321.0, this.reading.getMeterCount());
    }

    @Test
    public void setMeterIdTest() {
        Assertions.assertNotEquals("abc", this.reading.getMeterId());

        this.reading.setMeterId("abc");

        Assertions.assertEquals("abc", this.reading.getMeterId());
    }

    @Test
    public void setSubstituteTest() {
        Assertions.assertFalse(this.reading.getSubstitute());

        this.reading.setSubstitute(true);

        Assertions.assertTrue(this.reading.getSubstitute());
    }

    @Test
    public void getIdTest() {
        Assertions.assertEquals(UUID.fromString("b5afea1e-375c-4d3e-8124-007e5d3257d8"), reading.getId());
    }

    @Test
    public void setIdTest() {
        UUID id = UUID.randomUUID();

        Assertions.assertNotEquals(id, this.reading.getId());

        this.reading.setId(id);

        Assertions.assertEquals(id, this.reading.getId());
    }

    @Test
    public void equalsTest() {
        Reading reading = this.reading.clone();
        Assertions.assertEquals(reading, this.reading);
    }

    @Test
    public void equalsIdTest() {
        Reading reading = this.reading.clone();

        reading.setId(UUID.randomUUID());

        Assertions.assertNotEquals(reading, this.reading);
    }

    @Test
    public void equalsDateOfReadingTest() {
        Reading reading = this.reading.clone();

        reading.setDateOfReading(LocalDate.now());

        Assertions.assertNotEquals(reading, this.reading);
    }

    @Test
    public void equalsMeterIdTest() {
        Reading reading = this.reading.clone();

        reading.setMeterId("abc");

        Assertions.assertNotEquals(reading, this.reading);
    }

    @Test
    public void equalsKindOfMeterTest() {
        Reading reading = this.reading.clone();

        reading.setKindOfMeter(IReading.KindOfMeter.UNBEKANNT);

        Assertions.assertNotEquals(reading, this.reading);
    }

    @Test
    public void equalsMeterCountTest() {
        Reading reading = this.reading.clone();

        reading.setMeterCount(321.0);

        Assertions.assertNotEquals(reading, this.reading);
    }

    @Test
    public void equalsSubstituteTest() {
        Reading reading = this.reading.clone();

        reading.setSubstitute(true);

        Assertions.assertNotEquals(reading, this.reading);
    }

    @Test
    public void equalsCommentTest() {
        Reading reading = this.reading.clone();

        reading.setComment("new comment");

        Assertions.assertNotEquals(reading, this.reading);
    }

    @Test
    public void equalsCustomerTest() {
        Reading reading = this.reading.clone();

        Customer newCustomer = this.customer.clone();
        newCustomer.setId(UUID.randomUUID());
        customerDao.insert(newCustomer);

        reading.setCustomer(newCustomer);

        Assertions.assertNotEquals(reading, this.reading);
    }

    @Test
    public void equalsCustomerNullTest() {
        Reading reading = this.reading.clone();

        Customer newCustomer = this.customer.clone();
        newCustomer.setId(UUID.randomUUID());
        customerDao.insert(newCustomer);

        reading.setCustomer(newCustomer);

        customerDao.deleteById(this.customer.getId());

        Assertions.assertNotEquals(reading, this.reading);
    }

    @Test
    public void equalsCustomerBothNullTest() {
        Reading reading = this.reading.clone();

        customerDao.deleteById(this.customer.getId());

        Assertions.assertEquals(reading, this.reading);
    }
}

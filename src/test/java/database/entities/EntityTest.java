package database.entities;

import database.DatabaseTest;
import interfaces.ICustomer;
import interfaces.IReading;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.UUID;

public class EntityTest extends DatabaseTest {
    protected Reading reading;
    protected Customer customer;

    @BeforeEach
    @Override
    public void setup() {
        super.setup();

        this.customer = new Customer(
                UUID.fromString("49ebadf8-b64e-4b7d-8d98-000180558d7e"),
                ICustomer.Gender.U,
                "first name",
                "last name",
                LocalDate.of(2024, 12, 6)
        );
        customerDao.insert(customer);

        this.reading = new Reading(
                UUID.fromString("b5afea1e-375c-4d3e-8124-007e5d3257d8"),
                customer,
                LocalDate.of(2024, 12, 6),
                "123",
                123.0,
                IReading.KindOfMeter.WASSER,
                "comment",
                false
        );
        readingDao.insert(reading);
    }
}

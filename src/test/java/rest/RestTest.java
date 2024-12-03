package rest;

import database.daos.CustomerDao;
import database.daos.ReadingDao;
import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;
import interfaces.IReading;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.UUID;

public class RestTest {

    WebTarget target;
    protected static CustomerDao customerDao;
    protected static ReadingDao readingDao;

    String customerUUID = "ec617965-88b4-4721-8158-ee36c38e4db3";

    @BeforeAll
    public static void beforeAll() {
        customerDao = new CustomerDao();
        readingDao = new ReadingDao();
    }

    @BeforeEach
    public void setUp() {
        this.target = ClientBuilder.newClient().target("http://localhost:8080");
    }

    private Customer createCustomer() {
        Customer customer = new Customer(UUID.fromString(customerUUID), ICustomer.Gender.M, "Pumukel", "Kobold", LocalDate.of(1962, 02, 21));
        customerDao.insert(customer);

        return customer;
    }

    private Reading createReading() {
        Reading reading = new Reading(UUID.randomUUID(), UUID.fromString(customerUUID), LocalDate.now(), "Xr-Test-Meter", 200d, IReading.KindOfMeter.HEIZUNG, "", false);
        readingDao.insert(reading);

        return reading;
    }

}

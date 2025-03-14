package rest;

import database.DatabaseConnection;
import database.Property;
import database.daos.CustomerDao;
import database.daos.ReadingDao;
import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;
import interfaces.IDatabaseConnection;
import interfaces.IReading;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.UUID;

public class RestTest extends JerseyTest {
    protected static CustomerDao customerDao;
    protected static ReadingDao readingDao;
    protected static IDatabaseConnection connection;

    String customerUUID = "ec617965-88b4-4721-8158-ee36c38e4db3";
    String readingUUID = "4d4c9cbd-547a-4d6d-baa7-11ef5ef8ace4";

    @BeforeAll
    public static void beforeAll() {
        connection = (new DatabaseConnection()).openConnection(Property.readTestProperties());
        customerDao = new CustomerDao();
        readingDao = new ReadingDao();
    }

    @BeforeEach
    public void beforeEach() {
        connection.createAllTables();
    }

    protected Customer createCustomer() {
        Customer customer = new Customer(UUID.fromString(customerUUID), ICustomer.Gender.M, "Pumukel", "Kobold", LocalDate.of(1962, 02, 21));
        customerDao.insert(customer);

        return customer;
    }

    protected Reading createReading() {
        Customer customer = customerDao.findById(UUID.fromString(customerUUID));
        Reading reading = new Reading(UUID.fromString(readingUUID), customer, LocalDate.of(2024, 12, 6), "Xr-Test-Meter", 200d, IReading.KindOfMeter.HEIZUNG, "", false);
        readingDao.insert(reading);

        return reading;
    }

    @Override
    protected Application configure() {
        final String pack = "rest";
        final ResourceConfig rc = new ResourceConfig().packages(pack);
        rc.register(rest.KindOfMeterParamConverterProvider.class);
        rc.register(rest.LocalDateParamConverterProvider.class);
        rc.property(ServerProperties.PROVIDER_PACKAGES, "rest");
        return rc;
    }

}

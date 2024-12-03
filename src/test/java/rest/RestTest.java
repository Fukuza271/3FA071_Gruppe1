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
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Application;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.UUID;

public class RestTest extends JerseyTest {

    protected static WebTarget target;
    protected static CustomerDao customerDao;
    protected static ReadingDao readingDao;
    protected static IDatabaseConnection connection;

    String customerUUID = "ec617965-88b4-4721-8158-ee36c38e4db3";

    @BeforeAll
    public static void beforeAll() {
        connection = (new DatabaseConnection()).openConnection(Property.readTestProperties());
        customerDao = new CustomerDao();
        readingDao = new ReadingDao();
    }

    protected Customer createCustomer() {
        Customer customer = new Customer(UUID.fromString(customerUUID), ICustomer.Gender.M, "Pumukel", "Kobold", LocalDate.of(1962, 02, 21));
        customerDao.insert(customer);

        return customer;
    }

    protected Reading createReading() {
        Reading reading = new Reading(UUID.randomUUID(), UUID.fromString(customerUUID), LocalDate.now(), "Xr-Test-Meter", 200d, IReading.KindOfMeter.HEIZUNG, "", false);
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

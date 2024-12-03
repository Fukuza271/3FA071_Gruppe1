package rest;

import database.daos.CustomerDao;
import database.entities.Customer;
import interfaces.ICustomer;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerResourceTest {

    String testCustomerUUID = "ec617965-88b4-4721-8158-ee36c38e4db3";
    CustomerDao dao = new CustomerDao();
    WebTarget target;

    @BeforeEach
    public void setUp() {
        this.target = ClientBuilder.newClient().target("http://localhost:8080");

        //id, gender, firstname, lastname, birthdate
        Customer testCustomer = new Customer(UUID.fromString(testCustomerUUID), ICustomer.Gender.M, "Pumukel", "Kobold", LocalDate.of(1962, 02, 21));
        dao.insert(testCustomer);
    }

    @BeforeAll
    public static void setupOnce() {

    }

    @Test
    public void indexTest() {
     //   Assertions.assertEquals();
    }


    @Test
    public void showTest() {

        Response response = target.path("customers").path(testCustomerUUID).request(MediaType.
                APPLICATION_JSON).get();

        Assertions.assertEquals("""
        {"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"}""", response.readEntity(String.class));

    }

    @Test
    public void destroyTest() {

        Response response = target.path("customers").path(testCustomerUUID).request(MediaType.
                APPLICATION_JSON).get();

        Assertions.assertEquals("""
        {"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"}""", response.readEntity(String.class));


        response = target.path("customers").path(testCustomerUUID).request(MediaType.
                APPLICATION_JSON).delete();

        Assertions.assertNull(response.readEntity(String.class));

    }


    @Test
    public void storeTest() {

    }


}

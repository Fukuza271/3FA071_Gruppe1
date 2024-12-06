package rest;

import database.entities.Customer;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerResourceTest extends RestTest {

    @Test
    public void indexTest() {

        Customer customer = createCustomer();

        Response response = target().path("customers")
                .request(MediaType.APPLICATION_JSON).get();

        Assertions.assertEquals(customer, response.readEntity(Customer.class));
    }

    @Test
    public void showTest() {

        Customer customer = createCustomer();

        Response response = target().path("customers")
                .path(customerUUID)
                .request(MediaType.APPLICATION_JSON).get();

        Assertions.assertEquals(customer, response.readEntity(Customer.class));
    }

    @Test
    public void destroyTest() {

        Response response = target().path("customers").path(customerUUID).request(MediaType.
                APPLICATION_JSON).get();

        Assertions.assertEquals("""
                {"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"}""", response.readEntity(String.class));


        response = target().path("customers").path(customerUUID).request(MediaType.
                APPLICATION_JSON).delete();

        Assertions.assertEquals("""
                {"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"}""", response.readEntity(String.class));
    }


    @Test
    public void storeTest() {

    }

    @Test
    public void updateTest() {

    }


}

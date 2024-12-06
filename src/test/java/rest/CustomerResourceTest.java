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

        Response response = target()
                .path("customers")
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("""
                [{"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"}]""", response.readEntity(String.class));
    }

    @Test
    public void showTest() {
        Customer customer = createCustomer();

        Response response = target().path("customers")
                .path(customerUUID)
                .request(MediaType.APPLICATION_JSON).get();

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(customer, response.readEntity(Customer.class));
    }

    @Test
    public void destroyTest() {
        createCustomer();
        createReading();

        Response response = target()
                .path("customers")
                .path(customerUUID)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("""
                {"readings":[{"id":"4d4c9cbd-547a-4d6d-baa7-11ef5ef8ace4","customer":null,"dateOfReading":"2024-12-06","meterId":"Xr-Test-Meter","meterCount":200.0,"kindOfMeter":"HEIZUNG","comment":"","substitute":false}],"customer":{"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"}}""", response.readEntity(String.class));
        Assertions.assertEquals(0, customerDao.findAll().size());
    }
}

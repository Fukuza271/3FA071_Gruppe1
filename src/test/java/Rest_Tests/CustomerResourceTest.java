package Rest_Tests;

import Database_Tests.BasicTests;
import database.daos.CustomerDao;
import database.entities.Customer;
import interfaces.ICustomer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerResourceTest {

    String testCustomerUUID = "ec617965-88b4-4721-8158-ee36c38e4db3";
    CustomerDao dao = new CustomerDao();

    @BeforeEach


   @Test
   public void storeTest() {

    }

    @Test
    public void showTest() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        Response response = target.path("customers").path(testCustomerUUID).request(MediaType.
                APPLICATION_JSON).get();

        System.out.println(response.readEntity(String.class));

    }

    @Test
    public void destroyTest() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        Response response = target.path("customers").path(testCustomerUUID).request(MediaType.
                APPLICATION_JSON).delete();

        System.out.println(response.readEntity(String.class));

    }


}

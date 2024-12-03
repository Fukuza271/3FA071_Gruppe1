package rest;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseSetupTest {

    @Test
    public void setupTest() {
        WebTarget target;
        target = ClientBuilder.newClient().target("http://localhost:8080");
        Response response = target.path("setupDB").request().get();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

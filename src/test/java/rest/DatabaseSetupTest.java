package rest;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.client.Client;
import rest.DatabaseSetup;
import rest.Server;

import java.net.URI;

public class DatabaseSetupTest extends RestTest {

    @Test
    public void setupTest2() {
        Response response = this.target().path("setupDB").request().delete();
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}

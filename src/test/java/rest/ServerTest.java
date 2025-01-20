package rest;

import com.sun.net.httpserver.HttpServer;
import database.DatabaseConnection;
import database.Property;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;

public class ServerTest extends RestTest{

    private static HttpServer server;

    @Test
    public void startServer() throws IOException {
        (new DatabaseConnection()).openConnection(Property.readProperties());

        final String pack = "rest";
        URI url = URI.create("http://localhost:8080/");
        final ResourceConfig rc = new ResourceConfig().packages(pack);
        rc.register(rest.KindOfMeterParamConverterProvider.class);
        rc.register(rest.LocalDateParamConverterProvider.class);
        rc.property(ServerProperties.PROVIDER_PACKAGES, "rest");
        server = JdkHttpServerFactory.createHttpServer(url, rc);

        Assertions.assertDoesNotThrow(Server::startServer);
    }

    @Test
    public void stopServer() throws IOException{
        server.stop(0);
        (new DatabaseConnection()).closeConnection();
        Assertions.assertDoesNotThrow(Server::stopServer);
    }

}

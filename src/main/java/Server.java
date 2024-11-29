import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpServer;
import database.DatabaseConnection;
import database.Property;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rest.KindOfMeterParamConverter;

import java.net.URI;

public class Server {
    private static HttpServer server;

    public static void startServer() {
        (new DatabaseConnection()).openConnection(Property.readProperties());

        final String pack = "rest";
        URI url = URI.create("http://localhost:8080/");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        final ResourceConfig rc = new ResourceConfig().packages(pack).
                register(KindOfMeterParamConverter.class).
                register(new JacksonJaxbJsonProvider(mapper, JacksonJaxbJsonProvider.DEFAULT_ANNOTATIONS));
        rc.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        rc.property(ServerProperties.BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        server = JdkHttpServerFactory.createHttpServer(url, rc);
    }

    public static void stopServer() {
        server.stop(0);
        (new DatabaseConnection()).closeConnection();
    }

    public static void main(String[] args) {
        startServer();
    }
}

import com.sun.net.httpserver.HttpServer;
import database.DatabaseConnection;
import database.Property;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Server {
    private static HttpServer server;

    public static void startServer() {
        (new DatabaseConnection()).openConnection(Property.readProperties());

        final String pack = "rest";
        URI url = URI.create("http://localhost:8080/");
        final ResourceConfig rc = new ResourceConfig().packages(pack);

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

package rest;

import com.sun.net.httpserver.HttpServer;
import database.DatabaseConnection;
import database.Property;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import java.net.URI;
import java.util.Properties;
import java.util.UUID;

public class Server {
    public static HttpServer server;

    public static void startServer(Properties properties) { //Startet den server
        (new DatabaseConnection()).openConnection(properties);

        final String pack = "rest";
        URI url = URI.create("http://localhost:8080/");
        final ResourceConfig rc = new ResourceConfig().packages(pack);
        rc.register(rest.KindOfMeterParamConverterProvider.class);
        rc.register(rest.LocalDateParamConverterProvider.class);
        rc.property(ServerProperties.PROVIDER_PACKAGES, "rest");
        server = JdkHttpServerFactory.createHttpServer(url, rc);
    }

    public static void stopServer() {   //Beendet den Server
        server.stop(0);
        (new DatabaseConnection()).closeConnection();
        server = null;
    }

    public static void main(String[] args) {    //Bloß zum testen. könnte eiglt entfernt werden
        startServer(Property.readProperties());
    }
}

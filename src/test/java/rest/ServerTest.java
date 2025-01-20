package rest;

import database.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

public class ServerTest {
    @Test
    public void startServer() {
        Assertions.assertNull(Server.server);
        Assertions.assertDoesNotThrow(() -> Server.startServer(Property.readTestProperties()));
        Assertions.assertNotNull(Server.server);
        Server.stopServer();
    }

    @Test
    public void stopServer() {
        Server.startServer(Property.readTestProperties());
        Assertions.assertNotNull(Server.server);
        Assertions.assertDoesNotThrow(Server::stopServer);
        Assertions.assertNull(Server.server);
    }
}

package rest;

import database.DatabaseConnection;
import database.ExampleDataProvider;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("setupDB")
public class DatabaseSetup {
    @DELETE
    public Response dbSetup() {
        (new DatabaseConnection()).createAllTables();

        ExampleDataProvider.shared.insertCustomerData();
        ExampleDataProvider.shared.insertHeizungReadingData();
        ExampleDataProvider.shared.insertWasserReadingData();
        ExampleDataProvider.shared.insertStromReadingData();

        return Response.status(Response.Status.OK).build();
    }
}

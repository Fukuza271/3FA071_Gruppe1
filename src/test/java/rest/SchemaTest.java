package rest;

import com.fasterxml.jackson.databind.jsonschema.JsonSchema;
import database.entities.Customer;
import database.entities.Reading;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SchemaTest extends RestTest{

    @Test
    public void customerSchema() throws IOException {
        Schema schema = createSchema("customerSchema.json");
        Response response = target().path("customers")
                .path(customerUUID)
                .request(MediaType.APPLICATION_JSON).get();
        JSONObject jsonObject = new JSONObject(new JSONTokener(response.readEntity(String.class)));
        Assertions.assertDoesNotThrow(() -> schema.validate(jsonObject));
    }

    @Test
    public void readingSchema() throws IOException {
        Customer customer = createCustomer();
        customerDao.insert(customer);
        Reading reading = createReading();
        readingDao.insert(reading);
        Schema schema = createSchema("readingSchema.json");
        Response response = target().path("readings/" + reading.getId())
                .path(customerUUID)
                .request(MediaType.APPLICATION_JSON).get();
        System.out.println(response.getStatus());
        String entity = response.readEntity(String.class);
        JSONObject jsonObject = new JSONObject(new JSONTokener(entity));
        Assertions.assertDoesNotThrow(() -> schema.validate(jsonObject));
    }

    private Schema createSchema(String fileName) throws IOException {
        String homeDir = System.getProperty("user.dir");
        Path filePath = Path.of(homeDir, File.separator, File.separator, "src", File.separator, File.separator, "test", File.separator, File.separator, "resources", File.separator, File.separator, fileName);
        String stringSchema = Files.readString(filePath);
        JSONObject jsonSchema = new JSONObject(new JSONTokener(stringSchema));
        return SchemaLoader.load(jsonSchema);
    }
}

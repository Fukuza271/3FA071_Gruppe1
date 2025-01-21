package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import database.entities.Reading;
import interfaces.IReading;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class ReadingResourceTest extends RestTest {

    @Test
    public void indexTest() {
        createCustomer();
        createReading();

        Response response = target()
                .path("readings")
                .request(MediaType.APPLICATION_JSON)
                .get();


        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("""
                [{"id":"4d4c9cbd-547a-4d6d-baa7-11ef5ef8ace4","customer":{"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"},"dateOfReading":"2024-12-06","meterId":"Xr-Test-Meter","meterCount":200.0,"kindOfMeter":"HEIZUNG","comment":"","substitute":false}]""", response.readEntity(String.class));

    }

    @Test
    public void showTest() {
        createCustomer();
        createReading();

        Response response = target()
                .path("readings")
                .path(readingUUID)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("""
                {"id":"4d4c9cbd-547a-4d6d-baa7-11ef5ef8ace4","customer":{"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"},"dateOfReading":"2024-12-06","meterId":"Xr-Test-Meter","meterCount":200.0,"kindOfMeter":"HEIZUNG","comment":"","substitute":false}""", response.readEntity(String.class));
    }

    @Test
    public void destroyTest() {
        createCustomer();
        createReading();

        Response response = target()
                .path("readings")
                .path(readingUUID)
                .request(MediaType.APPLICATION_JSON)
                .delete();

        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals("""
                {"id":"4d4c9cbd-547a-4d6d-baa7-11ef5ef8ace4","customer":{"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"},"dateOfReading":"2024-12-06","meterId":"Xr-Test-Meter","meterCount":200.0,"kindOfMeter":"HEIZUNG","comment":"","substitute":false}""", response.readEntity(String.class));
        Assertions.assertEquals(0, readingDao.findAll().size());
    }
}

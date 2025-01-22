package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;
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

    @Test
    public void updateTest() {
        createCustomer();
        Reading reading = new Reading(UUID.fromString(readingUUID),new Customer(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"),ICustomer.Gender.M,"Pumukel","Kobold",LocalDate.of(1962,2,21)),LocalDate.parse("2024-12-06"),"Xr-Test-Meter",200.0, IReading.KindOfMeter.HEIZUNG,"",false);
        Response response = target()
                .path("readings")
                .request(MediaType.TEXT_PLAIN)
                .put(Entity.entity(reading, MediaType.APPLICATION_JSON));
        response.readEntity(String.class);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(1, readingDao.findAll().size());
    }


//    @Test
//    public void storeNewReadingTest(){
//        createReading();
//        Reading readingToBeStored = new Reading(,UUID.fromString("c2368509-e0f4-44ab-9bc2-02406f09a0d0"),
//                );
//
//        Response response = target().path("readings")
//                .request(MediaType.APPLICATION_JSON).post(Entity.entity(readingToBeStored,
//                        MediaType.APPLICATION_JSON));
//
//        Assertions.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
//        Assertions.assertEquals(2, readingDao.findAll().size());
//    }
}

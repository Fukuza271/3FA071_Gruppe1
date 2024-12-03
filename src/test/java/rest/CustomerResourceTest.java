package rest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerResourceTest extends RestTest {

    @Test
    public void indexTest() {
        //Gibt Liste aller Customer aus


        Response response = target.path("customers")
                .path(customerUUID)
                .request(MediaType.APPLICATION_JSON).get();

        Assertions.assertEquals("""
                ]{"id":"ec617965-88b4-4721-8158-ee36c38e4db3", "gender":"M","firstName":"Pumukel","lastName":"Kobold","birthdate":"1962-02-21"}
                """, response.readEntity(String.class));

    }


    @Test
    public void showTest() {

        Response response = target.path("customers").path(customerUUID).request(MediaType.
                APPLICATION_JSON).get();

        Assertions.assertEquals("""
        {"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"}""", response.readEntity(String.class));

    }

    @Test
    public void destroyTest() {

        Response response = target.path("customers").path(customerUUID).request(MediaType.
                APPLICATION_JSON).get();

        Assertions.assertEquals("""
        {"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"}""", response.readEntity(String.class));


        response = target.path("customers").path(customerUUID).request(MediaType.
                APPLICATION_JSON).delete();

        Assertions.assertEquals("""
        {"id":"ec617965-88b4-4721-8158-ee36c38e4db3","gender":"M","firstName":"Pumukel","lastName":"Kobold","birthDate":"1962-02-21"}""", response.readEntity(String.class));


    }


    @Test
    public void storeTest() {

    }

    @Test
    public void updateTest() {

    }


}

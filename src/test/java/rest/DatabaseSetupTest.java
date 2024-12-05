package rest;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DatabaseSetupTest extends RestTest {

    @Test
    public void dbSetupTest() {
        Assertions.assertEquals(0, customerDao.findAll().size());
        Assertions.assertEquals(0, readingDao.findAll().size());

        Response response = this.target()
                .path("setupDB")
                .request()
                .delete();


        Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assertions.assertEquals(1001, customerDao.findAll().size());
        Assertions.assertEquals(205, readingDao.findAll().size());
    }
}

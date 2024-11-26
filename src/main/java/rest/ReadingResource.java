package rest;

import database.daos.ReadingDao;
import database.entities.Reading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.awt.*;
import java.util.UUID;

@Path("readings")
public class ReadingResource {

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReading(@PathParam("uuid")UUID uuid) {
        ReadingDao readingDao = new ReadingDao();
        Reading reading = readingDao.findById(uuid);
        return Response.status(Response.Status.OK).entity(reading).build();
    }

    @POST
    public Response createReader() {
        ReadingDao readingDao = new ReadingDao();
        Reading reading = readingDao.insert()
    }
}

package rest;

import database.daos.ReadingDao;
import database.entities.Reading;
import interfaces.IReading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.awt.*;
import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("readings")
public class ReadingResource {

    ReadingDao readingDao = new ReadingDao();

    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReading(@PathParam("uuid")UUID uuid) {
        Reading reading = readingDao.findById(uuid);
        return Response.status(Response.Status.OK).entity(reading).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReader(Reading reading) {
        readingDao.insert(reading);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateReader(Reading reading) {
        readingDao.update(reading);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReader(@PathParam("uuid")UUID uuid) {
        readingDao.deleteById(uuid);
        return Response.status(Response.Status.OK).build();
    }

/*    @GET
    @Path("?customer={uuid}&start={start}&end={end}&kindOfMeter={kindOfMeter}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSpecificReader(@PathParam("uuid")UUID uuid,
                                         @PathParam("start")LocalDate start,
                                         @PathParam("end")LocalDate end,
                                         @PathParam("kindOfMeter")IReading.KindOfMeter kind) {

        if (start == null && end == null) {
            readingDao.where("meter_type", "=", kind.toString());
        }

    }*/
}

package rest;

import database.Condition;
import database.daos.ReadingDao;
import database.entities.Reading;
import interfaces.IReading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("readings")
public class ReadingResource {

    ReadingDao readingDao = new ReadingDao();

    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReading(@PathParam("uuid") UUID uuid) {
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
    public Response deleteReader(@PathParam("uuid") UUID uuid) {
        readingDao.deleteById(uuid);
        return Response.status(Response.Status.OK).build();
    }

    @GET
//    @Path("?customer={uuid}&start={start}&end={end}&kindOfMeter={kindOfMeter}")
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSpecificReader(@PathParam("uuid") UUID uuid,
                                         @QueryParam("start") LocalDate start,
                                         @QueryParam("end") LocalDate end,
                                         @QueryParam("kindOfMeter") IReading.KindOfMeter kind) {
        System.out.println(uuid+ ""+ start+""+""+ end+""+ kind);
        LocalDate startDate;
        LocalDate endDate;
        if (start == null) {
            startDate = LocalDate.of(1000, 12, 31);
        }
        else {
            startDate = start;
        }
        if (end == null) {
            endDate = LocalDate.now();
        }
        else {
            endDate = end;
        }
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("customer_id", "=", uuid.toString(), "AND"));
        conditions.add(new Condition("date", ">", startDate.toString(), "AND"));
        conditions.add(new Condition("date", "<", endDate.toString(), (!(kind instanceof IReading.KindOfMeter) ? "AND" : null)));
        if (!(kind instanceof IReading.KindOfMeter)) {
            conditions.add(new Condition("meter_type", "=", kind.toString(), null));
        }
        List<Reading> reading = readingDao.where(conditions);
        return Response.status(Response.Status.OK).entity(reading).build();
    }
}

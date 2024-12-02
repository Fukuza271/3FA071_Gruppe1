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
import java.util.Objects;
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
        if (reading == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(reading).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createReader(Reading reading) {
        if (readingDao.findById(reading.getId()) != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Reading with ID " + reading.getId() + " already exists")
                    .build();
        }
        readingDao.insert(reading);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateReader(Reading reading) {
        if (readingDao.findById(reading.getId()) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No reading with ID " + reading.getId() + " found")
                    .build();
        }
        readingDao.update(reading);
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReader(@PathParam("uuid") UUID uuid) {
        Reading reading = readingDao.findById(uuid);
        if (reading == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        readingDao.deleteById(uuid);
        return Response.status(Response.Status.OK).entity(reading).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchSpecificReader(@QueryParam("customer") UUID customer,
                                         @QueryParam("start") LocalDate start,
                                         @QueryParam("end") LocalDate end,
                                         @QueryParam("kindOfMeter") IReading.KindOfMeter kind) {
        System.out.println(customer+""+start+ end+ kind);
        LocalDate startDate;
        LocalDate endDate;
        startDate = Objects.requireNonNullElseGet(start, () -> LocalDate.of(1000, 12, 31));
        endDate = Objects.requireNonNullElseGet(end, LocalDate::now);
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("customer_id", "=", customer.toString(), "AND"));
        conditions.add(new Condition("date", ">", startDate.toString(), "AND"));
        conditions.add(new Condition("date", "<", endDate.toString(), (kind != null ? "AND" : null)));
        if (kind != null) {
            conditions.add(new Condition("meter_type", "=", kind.toString(), null));
        }
        List<Reading> reading = readingDao.where(conditions);
        return Response.status(Response.Status.OK).entity(reading).build();
    }
}

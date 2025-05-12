package rest;

import database.Condition;
import database.daos.ReadingDao;
import database.entities.Reading;
import interfaces.IReading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Path("readings")
public class ReadingResource {
    ReadingDao readingDao = new ReadingDao();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response show(@PathParam("id") String id) {  //Sucht ein spezifisches Reading
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Reading reading = readingDao.findById(uuid);

        if (reading == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(reading)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response store(Reading reading) {    //Speichert ein Reading
        if (readingDao.findById(reading.getId()) != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Reading with ID " + reading.getId() + " already exists")
                    .build();
        }
        readingDao.insert(reading);
        return Response.status(Response.Status.CREATED).entity(reading).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(Reading reading) {   //Updated ein Reading
        System.out.println(reading);
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
    public Response destroy(@PathParam("uuid") UUID uuid) { //Löscht/Zerstört ein reading
        Reading reading = readingDao.findById(uuid);
        if (reading == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        readingDao.deleteById(uuid);
        return Response.status(Response.Status.OK).entity(reading).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(  //Baut alle Readings
                            @QueryParam("customer") UUID customerId,
                            @QueryParam("start") LocalDate start,
                            @QueryParam("end") LocalDate end,
                            @QueryParam("kindOfMeter") IReading.KindOfMeter kind
    ) {
        LocalDate startDate = Objects.requireNonNullElseGet(start, () -> LocalDate.of(1000, 12, 31));
        LocalDate endDate = Objects.requireNonNullElseGet(end, LocalDate::now);

        List<Condition> conditions = new ArrayList<>();

        if (customerId != null) {
            conditions.add(new Condition("customer_id", "=", customerId.toString(), "AND"));
        }

        conditions.add(new Condition("date", ">", startDate.toString(), "AND"));
        conditions.add(new Condition("date", "<=", endDate.toString(), (kind != null ? "AND" : null)));

        if (kind != null) {
            conditions.add(new Condition("meter_type", "=", kind.toString(), null));
        }

        List<Reading> reading = readingDao.where(conditions);

        return Response
                .status(Response.Status.OK)
                .entity(reading)
                .build();
    }
}

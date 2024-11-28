package rest;

import database.daos.CustomerDao;
import database.entities.Customer;
import database.entities.Reading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("customers")
public class CustomerResource {
    private static final CustomerDao dao = new CustomerDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index() {
        List<Customer> customers = dao.findAll();
        return Response.status(Response.Status.OK).entity(customers).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response show(@PathParam("id") String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Customer customer = dao.findById(uuid);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(customer).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response destroy(@PathParam("id") String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Customer customer = dao.findById(uuid);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("customer", customer);
        result.put("readings", customer.getReadings());

        dao.deleteById(customer.getId());

        return Response.status(Response.Status.OK).entity(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    public Response store(Customer customer) {
        if (dao.findById(customer.getId()) != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer with ID " + customer.getId() + " already exists")
                    .build();
        }

        dao.insert(customer);

        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(Customer customer) {
        if (dao.findById(customer.getId()) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No customer with ID " + customer.getId() + " found")
                    .build();
        }

        dao.update(customer);

        return Response.status(Response.Status.OK).build();
    }
}

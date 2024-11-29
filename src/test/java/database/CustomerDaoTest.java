package database;

import database.entities.Customer;
import interfaces.ICustomer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CustomerDaoTest extends DatabaseTest {

    private Customer createCustomer() {
        Customer customer = new Customer(UUID.randomUUID(), ICustomer.Gender.W, "Jane", "Doe", LocalDate.now());
        customerDao.insert(customer);

        return customer;
    }

    @Test
    public void findByIdTest() {
        Customer customer = createCustomer();
        Assertions.assertNotNull(customerDao.findById(customer.getId()));
    }

    @Test
    public void insertTest() {
        Customer customer = new Customer(UUID.randomUUID(), ICustomer.Gender.W, "Jane", "Doe", LocalDate.now());

        customerDao.insert(customer);

        Assertions.assertNotNull(customerDao.findById(customer.getId()));
    }

    @Test
    public void updateTest() {
        Customer customer = createCustomer();

        Assertions.assertEquals("Doe", customer.getLastName());

        customer.setLastName("Mustermann");
        customerDao.update(customer);

        Customer updatedCustomer = customerDao.findById(customer.getId());
        Assertions.assertEquals("Mustermann", updatedCustomer.getLastName());
    }

    @Test
    public void deleteTest() {
        UUID customerId = createCustomer().getId();

        Assertions.assertNotNull(customerDao.findById(customerId));

        customerDao.deleteById(customerId);

        Assertions.assertNull(customerDao.findById(customerId));
    }

    @Test
    public void findAllTest() {
        Assertions.assertEquals(0, customerDao.findAll().size());

        createCustomer();
        createCustomer();
        createCustomer();

        Assertions.assertEquals(3, customerDao.findAll().size());
    }

    @Test
    public void whereTest() {
        Customer customer = createCustomer();
        createCustomer();

        List<Customer> customers = customerDao.where("id", "=", customer.getId().toString());
        Assertions.assertEquals(customers.getFirst().getId().toString(), customer.getId().toString());
        Assertions.assertEquals(customers.size(), 1);
    }
}

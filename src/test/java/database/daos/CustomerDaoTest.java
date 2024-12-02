package database.daos;

import database.DatabaseTest;
import database.entities.Customer;
import interfaces.ICustomer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public void whereCustomerTest() {
        List<Condition> conditions = new ArrayList<>();
        Customer customer = new Customer(UUID.randomUUID(), ICustomer.Gender.M, "Schnieder", "Schnieder", LocalDate.now());
        Customer customer2 = new Customer(UUID.randomUUID(), ICustomer.Gender.M, "Günter", "Günter", LocalDate.now());
        customerDao.insert(customer);
        customerDao.insert(customer2);
        conditions.add(new Condition("lastname", "=", "Schnieder", "OR"));
        conditions.add(new Condition("lastname", "=", "Günter", null));
        List<Customer> readings = customerDao.where(conditions);
        Assertions.assertEquals(2, readings.size());
    }
}

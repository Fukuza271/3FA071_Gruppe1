package Database_Tests;

import database.daos.CustomerDao;
import database.entities.Customer;
import interfaces.ICustomer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class CRUDTest {

    @Test
    public void findByIdTest() {
        CustomerDao customerDao = new CustomerDao();
        TestData.insertCustomerTestData();

        // Beliebige UUID aus der Testdatenbank genommen
        Customer customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3"));
        Assertions.assertNotNull(customer);
        customer = customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db2"));
        Assertions.assertNull(customer);
    }

    @Test
    public void insertTest() {
        Customer testCustomer = new Customer(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3")
                , ICustomer.Gender.M
                , "Thomas"
                , "Bauer"
                , LocalDate.of(1989, 12, 03));
        CustomerDao customerDao = new CustomerDao();
        Assertions.assertNull(customerDao.findById(UUID.fromString("ec617965-88b4-4721-8158-ee36c38e4db3")));

    }
}

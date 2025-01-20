package database.entities;

import interfaces.ICustomer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class CustomerTest extends EntityTest {
    @Test
    public void getBirthdayTest() {
        Assertions.assertEquals(LocalDate.of(2024, 12, 6), this.customer.getBirthDate());
    }

    @Test
    public void getFirstNameTest() {
        Assertions.assertEquals("first name", this.customer.getFirstName());
    }

    @Test
    public void getGenderTest() {
        Assertions.assertEquals(ICustomer.Gender.U, this.customer.getGender());
    }

    @Test
    public void getLastNameTest() {
        Assertions.assertEquals("last name", this.customer.getLastName());
    }

    @Test
    public void setBirthDateTest() {
        LocalDate today = LocalDate.now();

        Assertions.assertNotEquals(today, this.customer.getBirthDate());

        this.customer.setBirthDate(today);

        Assertions.assertEquals(today, this.customer.getBirthDate());
    }

    @Test
    public void setFirstNameTest() {
        Assertions.assertNotEquals("new first name", this.customer.getFirstName());

        this.customer.setFirstName("new first name");

        Assertions.assertEquals("new first name", this.customer.getFirstName());
    }

    @Test
    public void setGenderTest() {
        Assertions.assertNotEquals(ICustomer.Gender.D, this.customer.getGender());

        this.customer.setGender(ICustomer.Gender.D);

        Assertions.assertEquals(ICustomer.Gender.D, this.customer.getGender());
    }

    @Test
    public void setLastNameTest() {
        Assertions.assertNotEquals("new last name", this.customer.getLastName());

        this.customer.setLastName("new last name");

        Assertions.assertEquals("new last name", this.customer.getLastName());
    }

    @Test
    public void getIdTest() {
        Assertions.assertEquals(UUID.fromString("49ebadf8-b64e-4b7d-8d98-000180558d7e"), customer.getId());
    }

    @Test
    public void setIdTest() {
        UUID id = UUID.fromString("49ebadf8-b64e-4b7d-8d98-000180558d7c");

        Assertions.assertNotEquals(id, this.customer.getId());

        this.customer.setId(id);

        Assertions.assertEquals(id, this.customer.getId());
    }

    @Test
    public void toStringTest() {
        Assertions.assertEquals("U, first name, last name, 2024-12-06", this.customer.toString());
    }

    @Test
    public void toStringBirthDateNullTest() {
        this.customer.setBirthDate(null);
        Assertions.assertEquals("U, first name, last name, ", this.customer.toString());
    }

    @Test
    public void equalsTest() {
        Customer customer = this.customer.clone();
        Assertions.assertEquals(customer, this.customer);
    }

    @Test
    public void equalsIdTest() {
        Customer customer = this.customer.clone();

        customer.setId(UUID.randomUUID());

        Assertions.assertNotEquals(customer, this.customer);
    }

    @Test
    public void equalsFirstNameTest() {
        Customer customer = this.customer.clone();

        customer.setFirstName("new first name");

        Assertions.assertNotEquals(customer, this.customer);
    }

    @Test
    public void equalsLastNameTest() {
        Customer customer = this.customer.clone();

        customer.setLastName("new last name");

        Assertions.assertNotEquals(customer, this.customer);
    }

    @Test
    public void equalsGenderTest() {
        Customer customer = this.customer.clone();

        customer.setGender(ICustomer.Gender.D);

        Assertions.assertNotEquals(customer, this.customer);
    }

    @Test
    public void equalsBirthDateTest() {
        Customer customer = this.customer.clone();

        customer.setBirthDate(LocalDate.now());

        Assertions.assertNotEquals(customer, this.customer);
    }

    @Test
    public void equalsBirthDateNullTest() {
        Customer customer = this.customer.clone();

        customer.setBirthDate(null);

        Assertions.assertNotEquals(customer, this.customer);
    }

    @Test
    public void equalsBirthDateNull2Test() {
        Customer customer = this.customer.clone();

        this.customer.setBirthDate(null);

        Assertions.assertNotEquals(customer, this.customer);
    }

    @Test
    public void equalsBirthDateBothNullTest() {
        this.customer.setBirthDate(null);
        Customer customer = this.customer.clone();

        Assertions.assertEquals(customer, this.customer);
    }
}

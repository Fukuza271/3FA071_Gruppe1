package database.entities;

import interfaces.ICustomer;

import java.time.LocalDate;
import java.util.UUID;

public class Customer implements ICustomer {
    private UUID id;
    private ICustomer.Gender gender;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;

    public Customer(UUID id, ICustomer.Gender gender, String firstName, String lastName, LocalDate birthdate) {
        this.id = id;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
    }

    @Override
    public LocalDate getBirthDate() {
        return this.birthdate;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public Gender getGender() {
        return this.gender;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setBirthDate(LocalDate birtDate) {
        this.birthdate = birtDate;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}

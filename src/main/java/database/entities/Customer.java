package database.entities;

import com.fasterxml.jackson.annotation.*;
import database.Condition;
import database.daos.ReadingDao;
import interfaces.ICustomer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonTypeName(value = "customer")
public class Customer implements ICustomer {
    private UUID id;
    private ICustomer.Gender gender;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;

    @JsonCreator
    public Customer(
            @JsonProperty("id") UUID id,
            @JsonProperty("gender") ICustomer.Gender gender,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("birthDate") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate birthdate
    ) {
        this.id = id != null ? id : UUID.randomUUID();
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

    @Override
    public String toString() {
        return this.gender.name() + ", " + this.firstName + ", " + this.lastName + ", " + (this.birthdate == null ? "" : this.birthdate.toString());
    }

    @JsonIgnore
    public List<Reading> getReadings() {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("customer_id", "=", this.id.toString(), null));
        return (new ReadingDao()).where(conditions);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Customer customer) &&
               customer.getId().equals(this.getId()) &&
               customer.getFirstName().equals(this.getFirstName()) &&
               customer.getLastName().equals(this.getLastName()) &&
               customer.getGender().equals(this.getGender()) &&
               ((customer.getBirthDate() == null && this.getBirthDate() == null) || (customer.getBirthDate() != null && this.getBirthDate() != null && customer.getBirthDate().equals(this.getBirthDate())));
    }
}

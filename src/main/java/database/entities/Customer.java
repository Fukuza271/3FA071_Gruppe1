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
public class Customer implements ICustomer, Cloneable { //implements ICustomer = es nimmt die objekte von ICustomer. Cloneable = Kann geklont werden
    private UUID id;                    //jeder Kunde hat eine einmalige Identifikationsnummer
    private ICustomer.Gender gender;    //Geschlecht
    private String firstName;           //Vorname
    private String lastName;            //Nachname
    private LocalDate birthdate;        //Geburtstag

    @JsonCreator
    public Customer(    //Erzeugt den Kunden aus einer JSON datei
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
    } //Getter und setter

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
    } //Getter und Setter ende

    @Override
    public String toString() {  //Gibt die Person als String zurück
        return this.gender.name() + ", " + this.firstName + ", " + this.lastName + ", " + (this.birthdate == null ? "" : this.birthdate.toString());
    }

    @JsonIgnore
    public List<Reading> getReadings() {    //Holt die readings die mit diesem Kunden verbunden sind
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition("customer_id", "=", this.id.toString(), null));
        return (new ReadingDao()).where(conditions);
    }

    @Override
    public boolean equals(Object obj) {     //sieht ob das gegebene Objekt dem aktuellen Gleicht
        return (obj instanceof Customer customer) &&
               customer.getId().equals(this.getId()) &&
               customer.getFirstName().equals(this.getFirstName()) &&
               customer.getLastName().equals(this.getLastName()) &&
               customer.getGender().equals(this.getGender()) &&
               ((customer.getBirthDate() == null && this.getBirthDate() == null) || (customer.getBirthDate() != null && this.getBirthDate() != null && customer.getBirthDate().equals(this.getBirthDate())));
    }

    @Override
    public Customer clone() { //Klont den aktuellen Kunden
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {

            throw new AssertionError();
        }
    }
}

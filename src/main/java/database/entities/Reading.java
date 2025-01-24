package database.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import database.daos.CustomerDao;
import interfaces.ICustomer;
import interfaces.IReading;

import java.time.LocalDate;
import java.util.UUID;

@JsonTypeName(value = "reading")
public class Reading implements IReading, Cloneable { //implements IReading = es nimmt die objekte von IReading. Cloneable = Kann geklont werden
    private UUID id;                        // Id
    private Customer customer;              // Nimmt einen Kunden
    private LocalDate dateOfReading;        // Setzt das auslese datum des Zählers
    private String meter_ID;                // die ID des Zählers
    private Double meterCount;              // der Wert des Zählers am ausgewählten Zietpunkt
    private KindOfMeter meter_type;         // Zählerart
    private String comment;                 // Kommentar
    private boolean substitute;             // Ob der Zähler getauscht wurde

    @JsonCreator
    public Reading(     //Erzeugt das Reading aus der JSON datei
            @JsonProperty("id") UUID id,
            @JsonProperty("customer") Customer customer,
            @JsonProperty("dateOfReading") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate date,
            @JsonProperty("meterId") String meter_ID,
            @JsonProperty("meterCount") Double meter_count,
            @JsonProperty("kindOfMeter") KindOfMeter meter_type,
            @JsonProperty("comment") String comment,
            @JsonProperty("substitute") boolean substitute
    ) {
        this.id = id;
        this.customer = customer;
        this.dateOfReading = date;
        this.meter_ID = meter_ID;
        this.meterCount = meter_count;
        this.meter_type = meter_type;
        this.comment = comment;
        this.substitute = substitute;
    }

    @Override
    public String getComment() {
        return this.comment;
    }   //Getter / Setter

    @Override
    public ICustomer getCustomer() {
        if (this.customer == null) {
            return null;
        }

        return new CustomerDao().findById(customer.getId());
    }

    @Override
    public LocalDate getDateOfReading() {
        return this.dateOfReading;
    }

    @Override
    public KindOfMeter getKindOfMeter() {
        return this.meter_type;
    }

    @Override
    public Double getMeterCount() {
        return this.meterCount;
    }

    @Override
    public String getMeterId() {
        return this.meter_ID;
    }

    @Override
    public Boolean getSubstitute() {
        return this.substitute;
    }

    @Override
    public String printDateOfReading() {
        return this.dateOfReading.toString();
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setCustomer(ICustomer customer) {
        this.customer = (Customer) customer;
    }

    @Override
    public void setDateOfReading(LocalDate dateOfReading) {
        this.dateOfReading = dateOfReading;
    }

    @Override
    public void setKindOfMeter(KindOfMeter kindOfMeter) {
        this.meter_type = kindOfMeter;
    }

    @Override
    public void setMeterCount(Double meterCount) {
        this.meterCount = meterCount;
    }

    @Override
    public void setMeterId(String meterId) {
        this.meter_ID = meterId;
    }

    @Override
    public void setSubstitute(Boolean substitute) {
        this.substitute = substitute;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }   // Getter / Setter ende

    @Override
    public boolean equals(Object obj) { //Sieht ob das gegebene Objekt ein Reading ist und ob es das selbe reading ist
        return (obj instanceof Reading reading) &&
               reading.getId().equals(this.getId()) &&
               reading.getDateOfReading().equals(this.getDateOfReading()) &&
               reading.getMeterId().equals(this.getMeterId()) &&
               reading.getKindOfMeter().equals(this.getKindOfMeter()) &&
               reading.getMeterCount().equals(this.getMeterCount()) &&
               reading.getComment().equals(this.getComment()) &&
               reading.getSubstitute().equals(this.getSubstitute()) &&
               ((reading.getCustomer() == null && this.getCustomer() == null) || (reading.getCustomer() != null && this.getCustomer() != null && reading.getCustomer().equals(this.getCustomer())));

    }

    @Override
    public Reading clone() { //Klont das Reading
        try {
            return (Reading) super.clone();
        } catch (CloneNotSupportedException e) {

            throw new AssertionError();
        }
    }
}

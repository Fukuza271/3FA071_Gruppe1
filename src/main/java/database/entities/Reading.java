package database.entities;

import database.daos.CustomerDao;
import interfaces.ICustomer;
import interfaces.IReading;

import java.time.LocalDate;
import java.util.UUID;

public class Reading implements IReading {
    private UUID id;
    private UUID customer_id;
    private LocalDate dateOfReading;
    private String meter_ID;
    private Double meterCount;
    private KindOfMeter meter_type;
    private String comment;
    private boolean substitute;

    public Reading(UUID id, UUID customer_id, LocalDate date, String meter_ID, Double meter_count, KindOfMeter meter_type, String comment, boolean substitute) {
        this.id = id;
        this.customer_id = customer_id;
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
    }

    @Override
    public ICustomer getCustomer() {
        return new CustomerDao().findById(this.customer_id);
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
        return "";
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setCustomer(ICustomer customer) {
        this.customer_id = customer.getId();
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
    }
}

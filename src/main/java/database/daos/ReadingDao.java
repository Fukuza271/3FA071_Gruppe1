package database.daos;

import database.entities.Reading;
import interfaces.IReading;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class ReadingDao extends DataAccessObject<Reading> {
    @Override
    public Reading findById(UUID id) {
        return this.findById("""
                SELECT id, customer_id, date, meter_ID, value, meter_type, comment
                FROM readings
                WHERE id = ?;
                """, id, this::createReadingEntity);
    }

    @Override
    public List<Reading> findAll() {
        return this.findAll("""
                SELECT id, customer_id, date, meter_ID, value, meter_type, comment
                FROM reading;
                """, this::createReadingEntity);
    }

    @Override
    public boolean insert(Reading entity) {
        return this.insert("""
                INSERT INTO reading (id, customer_id, date, meter_ID, value, meter_type, comment)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """, (PreparedStatement statement) -> {
            statement.setString(1, entity.getId().toString());
            statement.setString(2, entity.getCustomer().getId().toString());
            statement.setDate(3, Date.valueOf(entity.getDateOfReading()));
            statement.setString(4, entity.getMeterId());
            statement.setDouble(5, entity.getMeterCount());
            statement.setString(6, entity.getKindOfMeter().toString());
            statement.setString(7, entity.getComment());
        });
    }

    @Override
    public boolean update(Reading entity) {
        return this.update("""
                UPDATE customers
                SET customer_id    = ?,
                    date = ?,
                    meter_ID  = ?,
                    value = ?
                    meter_type = ?
                    comment = ?
                WHERE id = ?;
                """, (PreparedStatement statement) -> {
            statement.setString(7, entity.getId().toString());
            statement.setString(1, entity.getCustomer().getId().toString());
            statement.setDate(2, Date.valueOf(entity.getDateOfReading()));
            statement.setString(3, entity.getMeterId());
            statement.setDouble(4, entity.getMeterCount());
            statement.setString(5, entity.getKindOfMeter().toString());
            statement.setString(6, entity.getComment());
        });
    }

    @Override
    public boolean deleteById(UUID id) {
        return this.deleteById("DELETE FROM reading WHERE id = ?", id);
    }

    private Reading createReadingEntity(ResultSet rs) throws SQLException {
        return new Reading(
                UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("customer_id")),
                rs.getDate("date").toLocalDate(),
                rs.getString("meter_ID"),
                rs.getDouble("value"),
                IReading.KindOfMeter.valueOf(rs.getString("meter_type")),
                rs.getString("comment")
        );
    }
}


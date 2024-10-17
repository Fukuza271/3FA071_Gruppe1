package database.daos;

import database.entities.Reading;
import interfaces.IReading;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadingDao extends DataAccessObject<Reading> {
    @Override
    public Reading findById(UUID id) {

        try {
            PreparedStatement statement = this.getPreparedStatement("""
                    SELECT id, customer_id, date, meter_ID, value, meter_type, comment
                    FROM readings
                    WHERE id = ?;
                    """);

            statement.setString(1, id.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.first()) {
                return createReadingEntity(rs);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Reading> findAll() {
        List<Reading> results = new ArrayList<>();

        try {
            PreparedStatement statement = this.getPreparedStatement("""
                    SELECT id, customer_id, date, meter_ID, value, meter_type, comment
                    FROM reading;
                    """);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Reading reading = this.createReadingEntity(rs);
                results.add(reading);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return results;
    }

    @Override
    public boolean insert(Reading entity) {
        try {
            PreparedStatement statement = this.getPreparedStatement("""
                    INSERT INTO reading (id, customer_id, date, meter_ID, value, meter_type, comment)
                    VALUES (?, ?, ?, ?, ?, ?, ?);
                    """);

            statement.setString(1, entity.getId().toString());
            statement.setString(2, entity.getCustomer().getId().toString());
            statement.setDate(3, Date.valueOf(entity.getDateOfReading()));
            statement.setString(4, entity.getMeterId());
            statement.setDouble(5, entity.getMeterCount());
            statement.setString(6, entity.getKindOfMeter().toString());
            statement.setString(7, entity.getComment());

            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Reading entity) {
        try {
            PreparedStatement statement = this.getPreparedStatement("""
                    UPDATE customers
                    SET customer_id    = ?,
                        date = ?,
                        meter_ID  = ?,
                        value = ?
                        meter_type = ?
                        comment = ?
                    WHERE id = ?;
                    """);

            statement.setString(7, entity.getId().toString());
            statement.setString(1, entity.getCustomer().getId().toString());
            statement.setDate(2, Date.valueOf(entity.getDateOfReading()));
            statement.setString(3, entity.getMeterId());
            statement.setDouble(4, entity.getMeterCount());
            statement.setString(5, entity.getKindOfMeter().toString());
            statement.setString(6, entity.getComment());

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            PreparedStatement statement = this.getPreparedStatement("DELETE FROM reading WHERE id = ?");

            statement.setString(1, id.toString());
            statement.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

    private Reading createReadingEntity(ResultSet rs) throws SQLException {
        return new Reading(
                UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("customer_id")),
                rs.getDate("date").toLocalDate(),
                rs.getString("meter_ID"),
                rs.getDouble("value"),
                IReading.KindOfMeter.valueOf(rs.getString("meter_type")),
                rs.getString("comment"));
    }
}


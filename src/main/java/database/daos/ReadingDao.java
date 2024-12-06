package database.daos;

import database.Condition;
import database.entities.Reading;
import interfaces.IReading;
import jakarta.ws.rs.core.Response;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReadingDao extends DataAccessObject<Reading> {

    String sqlCustomerReadingsData = " id, customer_id, date, meter_ID, meter_count, meter_type, comment, substitute FROM readings ";

    @Override
    public Reading findById(UUID id) {
        return this.findById("""
                SELECT""" + sqlCustomerReadingsData + """
                WHERE id = ?;
                """, id, this::createReadingEntity);
    }

    @Override
    public List<Reading> findAll() {
        return this.get("""
                SELECT""" + sqlCustomerReadingsData + """
                ;
                """, this::createReadingEntity);
    }

    @Override
    public boolean insert(Reading entity) {
        return this.insert("""
                INSERT INTO readings (id, customer_id, date, meter_ID, meter_count, meter_type, comment, substitute)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                """, (PreparedStatement statement) -> {
            statement.setString(1, entity.getId().toString());
            statement.setString(2, entity.getCustomer().getId().toString());
            statement.setDate(3, Date.valueOf(entity.getDateOfReading()));
            statement.setString(4, entity.getMeterId());
            statement.setDouble(5, entity.getMeterCount());
            statement.setString(6, entity.getKindOfMeter().toString());
            statement.setString(7, entity.getComment());
            statement.setBoolean(8, entity.getSubstitute());
        });
    }

    @Override
    public boolean update(Reading entity) {
        return this.update("""
                UPDATE readings
                SET customer_id = ?,
                    date = ?,
                    meter_ID  = ?,
                    meter_count = ?,
                    meter_type = ?,
                    comment = ?,
                    substitute = ?
                WHERE id = ?;
                """, (PreparedStatement statement) -> {
            statement.setString(8, entity.getId().toString());
            statement.setString(1, (entity.getCustomer().getId().toString()));
            statement.setDate(2, Date.valueOf(entity.getDateOfReading()));
            statement.setString(3, entity.getMeterId());
            statement.setDouble(4, entity.getMeterCount());
            statement.setString(5, entity.getKindOfMeter().toString());
            statement.setString(6, entity.getComment());
            statement.setBoolean(7, entity.getSubstitute());
        });
    }

    @Override
    public boolean deleteById(UUID id) {
        return this.deleteById("DELETE FROM readings WHERE id = ?", id);
    }

    @Override
    public List<Reading> where(List<Condition> argList) {

        StringBuilder builder = new StringBuilder();
        List<String> valueList = new ArrayList<>();
        String baseSql = """
                SELECT""" + sqlCustomerReadingsData + """
                WHERE\s
                """;
        for (Condition condition : argList) {
            builder.append(condition.getColumn()).
                    append(" ").
                    append(condition.getOperator()).
                    append(" ?");
            builder.append(" ").append(condition.getLogicalOperator()).append(" ");
            valueList.add(condition.getValue());
        }
        builder.append(";");
        String sql = baseSql + builder;
        return this.get(sql, this::createReadingEntity, valueList);
    }

    private Reading createReadingEntity(ResultSet rs) {
        Reading reading = null;
        try {
            reading = new Reading(
                    UUID.fromString(rs.getString("id")),
                    UUID.fromString(rs.getString("customer_id")),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("meter_ID"),
                    rs.getDouble("meter_count"),
                    IReading.KindOfMeter.valueOf(rs.getString("meter_type")),
                    rs.getString("comment"),
                    rs.getBoolean("substitute")
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reading;
    }
}


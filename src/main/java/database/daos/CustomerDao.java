package database.daos;

import database.entities.Customer;
import interfaces.ICustomer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class CustomerDao extends DataAccessObject<Customer> {
    @Override
    public Customer findById(UUID id) {
        return this.findById("""
                SELECT id, gender, firstName, lastName, birthdate
                FROM customers
                WHERE id = ?;
                """, id, this::createCustomerEntity);
    }

    @Override
    public List<Customer> get() {
        return this.get("""
                SELECT id, gender, firstName, lastName, birthdate
                FROM customers;
                """, this::createCustomerEntity);
    }

    @Override
    public boolean insert(Customer entity) {
        return this.insert("""
                    INSERT INTO customers (id, gender, firstname, lastname, birthdate)
                    VALUES (?, ?, ?, ?, ?);
                """, (PreparedStatement statement) -> {
            statement.setString(1, entity.getId().toString());
            statement.setString(2, entity.getGender().toString());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setDate(5, Date.valueOf(entity.getBirthDate()));
        });
    }

    @Override
    public boolean update(Customer entity) {
        return this.update("""
                UPDATE customers
                SET gender    = ?,
                    firstname = ?,
                    lastname  = ?,
                    birthdate = ?
                WHERE id = ?;
                """, (PreparedStatement statement) -> {
            statement.setString(5, entity.getId().toString());
            statement.setString(1, entity.getGender().toString());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setDate(4, Date.valueOf(entity.getBirthDate()));
        });
    }

    @Override
    public boolean deleteById(UUID id) {
        return this.deleteById("DELETE FROM customers WHERE id = ?", id);
    }

    @Override
    public List<Customer> where(String column, String operator, String value) {
        return this.get(String.format("""
                SELECT id, gender, firstName, lastName, birthdate
                FROM customers
                WHERE %s %s '%s';
                """, column, operator, value), this::createCustomerEntity);
    }

    private Customer createCustomerEntity(ResultSet rs) throws SQLException {
        return new Customer(
                UUID.fromString(rs.getString("id")),
                ICustomer.Gender.valueOf(rs.getString("gender")),
                rs.getString("firstName"), rs.getString("lastName"),
                rs.getDate("birthdate").toLocalDate()
        );
    }
}

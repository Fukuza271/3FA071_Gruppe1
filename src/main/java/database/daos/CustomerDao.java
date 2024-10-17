package database.daos;

import database.entities.Customer;
import interfaces.ICustomer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

public class CustomerDao extends DataAccessObject<Customer> {
    @Override
    public Customer findById(UUID id) {
        try {
            PreparedStatement statement = this.getPreparedStatement("""
                    SELECT id, gender, firstName, lastName, birthdate
                    FROM customers
                    WHERE id = ?;
                    """);

            statement.setString(1, id.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.first()) {
                return createCustomerEntity(rs);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> results = new ArrayList<>();

        try {
            PreparedStatement statement = this.getPreparedStatement("""
                    SELECT id, gender, firstName, lastName, birthdate
                    FROM customers;
                    """);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Customer customer = this.createCustomerEntity(rs);
                results.add(customer);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return results;
    }

    @Override
    public boolean insert(Customer entity) {
        try {
            PreparedStatement statement = this.getPreparedStatement("""
                    INSERT INTO customers (id, gender, firstname, lastname, birthdate)
                    VALUES (?, ?, ?, ?, ?);
                    """);

            statement.setString(1, entity.getId().toString());
            statement.setString(2, entity.getGender().toString());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setDate(5, Date.valueOf(entity.getBirthDate()));

            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Customer entity) {
        try {
            PreparedStatement statement = this.getPreparedStatement("""
                    UPDATE customers
                    SET gender    = ?,
                        firstname = ?,
                        lastname  = ?,
                        birthdate = ?
                    WHERE id = ?;
                    """);

            statement.setString(5, entity.getId().toString());
            statement.setString(1, entity.getGender().toString());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setDate(4, Date.valueOf(entity.getBirthDate()));

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    @Override
    public boolean deleteById(UUID id) {
        try {
            PreparedStatement statement = this.getPreparedStatement("DELETE FROM customers WHERE id = ?");

            statement.setString(1, id.toString());
            statement.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

    private Customer createCustomerEntity(ResultSet rs) throws SQLException {
        return new Customer(
                UUID.fromString(rs.getString("id")),
                ICustomer.Gender.valueOf(rs.getString("gender")),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getDate("birthdate").toLocalDate());
    }
}

package database.daos;

import database.entities.Customer;
import interfaces.ICustomer;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CustomerDao extends DataAccessObject<Customer> {

    String sqlSelectCustomer = "SELECT id, gender, firstName, lastName, birthdate FROM customers ";

    @Override
    public Customer findById(UUID id) {
        if (this.findById(sqlSelectCustomer + """
                WHERE id = ?;
                """, id, this::createCustomerEntity) == null) {
            Customer c = new Customer(id, ICustomer.Gender.U, "Max", "Mustermann", LocalDate.of(1970, 1, 1));
            insert(c);
        }
        return this.findById(sqlSelectCustomer + """
                WHERE id = ?;
                """, id, this::createCustomerEntity);

    }

    @Override
    public List<Customer> findAll() {
        return this.get(sqlSelectCustomer + """
                ;
                """, this::createCustomerEntity);
    }

    @Override
    public boolean insert(Customer entity) {
        return this.insert("""
                INSERT INTO customers (id, gender, firstname, lastname, birthdate)
                VALUES (?, ?, ?, ?, ?)
                ON DUPLICATE KEY UPDATE gender    = ?,
                                        firstname = ?,
                                        lastname  = ?,
                                        birthdate = ?;
                """, (PreparedStatement statement) -> {
            statement.setString(1, entity.getId().toString());
            statement.setString(2, entity.getGender().toString());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setDate(5, entity.getBirthDate() == null ? null : Date.valueOf(entity.getBirthDate()));
            statement.setString(6, entity.getGender().toString());
            statement.setString(7, entity.getFirstName());
            statement.setString(8, entity.getLastName());
            statement.setDate(9, entity.getBirthDate() == null ? null : Date.valueOf(entity.getBirthDate()));
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

    private Customer createCustomerEntity(ResultSet rs) {
        Customer customer = null;
        try {
            Date birthdate = rs.getDate("birthdate");

            customer = new Customer(
                    UUID.fromString(rs.getString("id")),
                    ICustomer.Gender.valueOf(rs.getString("gender")),
                    rs.getString("firstName"), rs.getString("lastName"),
                    (birthdate != null) ? birthdate.toLocalDate() : null
            );
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return customer;
    }
}

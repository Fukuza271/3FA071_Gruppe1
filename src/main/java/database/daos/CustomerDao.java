package database.daos;

import database.DatabaseConnection;
import database.Property;
import database.entities.Customer;
import interfaces.ICustomer;
import interfaces.IDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerDao implements IDao<Customer> {
    private DatabaseConnection databaseConnection;

    public CustomerDao() {
        this.databaseConnection = new DatabaseConnection();
        this.databaseConnection.openConnection(Property.readProperties());
    }

    @Override
    public Customer findById(UUID id) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> results = new ArrayList<>();

        try {
            Statement statement = this.databaseConnection.connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM customers;");

            while (rs.next()) {
                Customer customer = new Customer(
                        UUID.fromString(rs.getString("id")),
                        ICustomer.Gender.valueOf(rs.getString("gender")),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("birthdate").toLocalDate());

                results.add(customer);
            }
        } catch (SQLException e) {
            return results;
        }

        return results;
    }

    @Override
    public void insert(Customer entity) {

    }

    @Override
    public void update(Customer entity) {

    }

    @Override
    public void deleteById(UUID id) {

    }
}

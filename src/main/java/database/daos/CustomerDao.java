package database.daos;

import database.DatabaseConnection;
import database.Property;
import database.entities.Customer;
import interfaces.IDao;
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
        return List.of();
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

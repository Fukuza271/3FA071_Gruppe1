package database.daos;

import database.DatabaseConnection;
import database.Property;
import interfaces.IDatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public abstract class DataAccessObject<T> {
    private final IDatabaseConnection databaseConnection;

    public DataAccessObject() {
        this.databaseConnection = new DatabaseConnection().openConnection(Property.readProperties());
    }

    protected PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.databaseConnection.getConnection().prepareStatement(sql);
    }

    abstract T findById(UUID id);

    abstract List<T> findAll();

    abstract boolean insert(T entity);

    abstract boolean update(T entity);

    abstract boolean deleteById(UUID id);
}

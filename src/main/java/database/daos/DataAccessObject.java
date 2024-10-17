package database.daos;

import database.DatabaseConnection;
import database.Property;
import interfaces.AddParamsToStatement;
import interfaces.IDatabaseConnection;
import interfaces.CreateEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DataAccessObject<T> {
    private final IDatabaseConnection databaseConnection;

    public DataAccessObject() {
        this.databaseConnection = new DatabaseConnection().openConnection(Property.readProperties());
    }

    private PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.databaseConnection.getConnection().prepareStatement(sql);
    }

    abstract T findById(UUID id);

    abstract List<T> findAll();

    abstract boolean insert(T entity);

    abstract boolean update(T entity);

    abstract boolean deleteById(UUID id);

    protected boolean insert(String sql, AddParamsToStatement addParams) {
        try {
            PreparedStatement statement = this.getPreparedStatement(sql);
            addParams.execute(statement);

            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }

    protected boolean update(String sql, AddParamsToStatement addParams) {
        try {
            PreparedStatement statement = this.getPreparedStatement(sql);
            addParams.execute(statement);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    protected T findById(String sql, UUID id, CreateEntity<T> createEntity) {
        try {
            PreparedStatement statement = this.getPreparedStatement(sql);

            statement.setString(1, id.toString());
            ResultSet rs = statement.executeQuery();

            if (rs.first()) {
                return createEntity.execute(rs);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return null;
    }

    protected List<T> findAll(String sql, CreateEntity<T> createEntity) {
        List<T> results = new ArrayList<>();

        try {
            PreparedStatement statement = this.getPreparedStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                T entity = createEntity.execute(rs);
                results.add(entity);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return results;
    }

    protected boolean deleteById(String sql, UUID id) {
        try {
            PreparedStatement statement = this.getPreparedStatement(sql);

            statement.setString(1, id.toString());
            statement.execute();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }

        return true;
    }
}

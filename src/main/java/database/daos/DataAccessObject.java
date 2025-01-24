package database.daos;

import database.Condition;
import database.DatabaseConnection;
import database.Property;
import database.entities.Customer;
import database.entities.Reading;
import interfaces.AddParamsToStatement;
import interfaces.CreateEntity;
import interfaces.IDatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class DataAccessObject<T> { //Basis Dao Klasse
    private final IDatabaseConnection databaseConnection;

    public DataAccessObject() { // Erzeugt die DAO entity
        this.databaseConnection = new DatabaseConnection().openConnection(Property.readProperties());
    }

    protected PreparedStatement getPreparedStatement(String sql) {  // Sendet das Statement an den Datenbankserver
        PreparedStatement statement = null;
        try {
            statement = this.databaseConnection.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return statement;
    }

    abstract T findById(UUID id);   // Abstrakte methoden (Erklärt im relevanten Dao)

    abstract List<T> findAll();

    abstract boolean insert(T entity);

    abstract boolean update(T entity);

    abstract boolean deleteById(UUID id);   // Ende Abstrakte Methoden

    protected boolean insert(String sql, AddParamsToStatement addParams) {  // fügt neuen Eintrag in die Datenbank
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

    protected boolean update(String sql, AddParamsToStatement addParams) {  // updated einen eintrag in der datenbank
        try {
            PreparedStatement statement = this.getPreparedStatement(sql);
            addParams.execute(statement);

            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    protected T findById(String sql, UUID id, CreateEntity<T> createEntity) { //sucht einen datenbank eintrag über die ID
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

    protected List<T> get(String sql, CreateEntity<T> createEntity) {   // für die Where befehle
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

    protected List<T> get(String sql, CreateEntity<T> createEntity, List<String> arguments) { //für die Where befehle

        List<T> results = new ArrayList<>();
        try {
            PreparedStatement statement = this.getPreparedStatement(sql);
            int index = 1;
            for (String innerList : arguments) {
                statement.setObject(index++, innerList);
            }
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

    protected boolean deleteById(String sql, UUID id) { //Löscht einen eintrag
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

    public abstract List<T> where(List<Condition> argList); //Abstrakte form der Where einträge
    
}

package database.daos;

import database.Condition;
import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerDao extends DataAccessObject<Customer> { //Enthält alle Methoden in DataAccessObject
    String sqlSelectCustomer = "SELECT id, gender, firstName, lastName, birthdate FROM customers "; //Simpler Select befehl der erweitert werden kann

    @Override
    public Customer findById(UUID id) { //Erweitert sqlSelectCustomer um die kondition dass die ID die angegebene sein muss
        return this.findById(sqlSelectCustomer + """
                WHERE id = ?;
                """, id, this::createCustomerEntity);

    }

    @Override
    public List<Customer> findAll() {   //Sucht einfach nach allen kunden
        return this.get(sqlSelectCustomer + """
                ;
                """, this::createCustomerEntity);
    }

    @Override
    public boolean insert(Customer entity) {    //Fügt den angegebenen Kunden in
        Date birthdate = entity.getBirthDate() != null ? Date.valueOf(entity.getBirthDate()) : null;

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
            statement.setDate(5, birthdate);
            statement.setString(6, entity.getGender().toString());
            statement.setString(7, entity.getFirstName());
            statement.setString(8, entity.getLastName());
            statement.setDate(9, birthdate);
        });
    }

    @Override
    public boolean update(Customer entity) { //Updated den Kunden dessen ID angegeben ist
        Date birthdate = entity.getBirthDate() != null ? Date.valueOf(entity.getBirthDate()) : null;

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
            statement.setDate(4, birthdate);
        });
    }

    @Override
    public boolean deleteById(UUID id) {
        return this.deleteById("DELETE FROM customers WHERE id = ?", id);
    } // Löscht den kunden dessen ID angegeben ist

    @Override
    public List<Customer> where(List<Condition> argList) {  // erweitert den select befehl um mindestens eine weitere Kondition

        StringBuilder builder = new StringBuilder();
        List<String> valueList = new ArrayList<>();
        String baseSql = """
                """ + sqlSelectCustomer + """
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
        return this.get(sql, this::createCustomerEntity, valueList);
    }


    private Customer createCustomerEntity(ResultSet rs) {   // erzeugt die Entity des Kunden
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

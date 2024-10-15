package Database_Tests;

import database.DatabaseConnection;
import database.Property;
import interfaces.IDatabaseConnection;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataTest {

    @Test
    public void testOpenConnection() throws SQLException {
        IDatabaseConnection connection = new DatabaseConnection();
        connection.openConnection(Property.readProperties());
    }

    @Test
    public void insertCustomerTestData() {

        DatabaseConnection connection = new DatabaseConnection();
        IDatabaseConnection result = connection.openConnection(Property.readProperties());
        Assert.assertNotNull(result);

        try {
            Statement statement = connection.connection.createStatement();

            connection.createAllTables();

            statement.execute("""
                        INSERT INTO customers(id, gender, firstName, lastName, birthdate) VALUES
                        ('ec617965-88b4-4721-8158-ee36c38e4db3', 'm', 'Pumukel', 'Kobold', '2003-10-10'),
                        ('848c39a1-0cbb-427a-ac6f-a88941943dc8', 'm', 'André', 'Schöne', '1995-11-30'),
                        ('78dff413-7409-4313-90db-5ec95e969d6d', 'w', 'Antje', 'Kittler', '1980-04-02'),
                        ('8670e527-3f5e-44cc-ae61-fba80268bd7f', 'w', 'Cindy', 'Gerhardt', '2000-06-15'),
                        ('2d7886be-8f4e-495c-a7f9-55d99ed273c9', 'w', 'Ismene', 'Lapp', '1997-12-07'),
                        ('092958db-b395-4865-9030-a22abdb17b8e', 'w', 'Marieluise', 'Augustin', '1999-01-31'),
                        ('a9938cec-0209-4b95-a0ce-e0a4afa5af88', 'u', 'Siglinde', 'Konietzko', '1975-02-12'),
                        ('f2683104-974d-44eb-a060-82ed72737cbe', 'd', 'Elgine', 'Karras', '1998-12-26'),
                        ('2a284519-4141-409c-a5d6-ad77bba13523', 'w', 'Karolina', 'Hamburger', '2001-04-23'),
                        ('b3da4b4c-c5dc-44a6-bebb-d82f711a6576', 'w', 'Roseline', 'Jäger', '1994-03-05');
                    """);

            connection.closeConnection();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

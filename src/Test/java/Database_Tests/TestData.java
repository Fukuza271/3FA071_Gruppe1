package Database_Tests;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import database.DatabaseConnection;
import database.Property;
import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TestData extends BasicTests {

    public static void insertCustomerTestData() {

        DatabaseConnection connection = new DatabaseConnection();
        connection.openConnection(Property.readProperties());

        try {
            Statement statement = connection.getConnection().createStatement();

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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void insertCsv() {
        List<List<String>> csvHeizungLines = readCSV("heizung.csv", ';');
        UUID kundenID;
        for (int i = 0; i < csvHeizungLines.size(); ++i) {
            List<String> innerList = csvHeizungLines.get(i);
            for (int j = 0; j < innerList.size(); ++j) {
                if (innerList.get(j).equals("Kunde")) {
                    kundenID = UUID.fromString(innerList.get(j + 1));
                    int k;
                }
            }
        }
    }

    public List<List<String>> readCSV(String fileName, char seperator) {

        String dirToCsv = System.getProperty("user.dir") + File.separator + "src" + File.separator + "Test" + File.separator + "resources" + File.separator;
        List<List<String>> result = new ArrayList<>();
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(dirToCsv + fileName)).withCSVParser(new CSVParserBuilder().withSeparator(seperator).build()).build();
            String[] splitResult;
            while ((splitResult = (reader.readNext())) != null) {
                result.add(Arrays.asList(splitResult));
            }
        } catch (RuntimeException | IOException | CsvValidationException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}

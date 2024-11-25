package Database_Tests;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import database.DatabaseConnection;
import database.Property;
import database.entities.Reading;
import database.daos.CustomerDao;
import database.entities.Customer;
import interfaces.ICustomer;
import interfaces.IReading;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;

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

    public static void insertHeizungReadingData() {
        insertReadingData(IReading.KindOfMeter.HEIZUNG, readCSV("heizung.csv", ';'));
    }

    public static void insertWasserReadingData() {
        insertReadingData(IReading.KindOfMeter.WASSER, readCSV("wasser.csv", ';'));
    }

    public static void insertStromReadingData() {
        insertReadingData(IReading.KindOfMeter.STROM, readCSV("strom.csv", ';'));
    }

    private static void insertReadingData(IReading.KindOfMeter type, List<List<String>> data) {
        UUID customerId = null;
        String meterId = null;

        for (List<String> innerList : data) {
            if (innerList.contains("Kunde")) {
                customerId = UUID.fromString(innerList.get(1));
                continue;
            }

            if (innerList.contains("Zählernummer")) {
                meterId = innerList.get(1);
                continue;
            }

            if (innerList.isEmpty() || innerList.contains("Datum") || innerList.getFirst().isEmpty()) {
                continue;
            }

            LocalDate date = LocalDate.parse(innerList.get(0), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            double meterCount = Double.parseDouble(innerList.get(1).replace(',', '.'));
            String comment = innerList.get(2);
            boolean substitute = comment.contains("Zählertausch");

            readingDao.insert(new Reading(
                    UUID.randomUUID(),
                    customerId,
                    date,
                    meterId,
                    meterCount,
                    type,
                    comment,
                    substitute
            ));
        }
    }

    public static void insertCustomerData() {
        readCSV("kunden_utf8.csv", ',').forEach(row -> {
            if (row.getFirst().equals("UUID")) {
                return;
            }

            UUID id = UUID.fromString(row.getFirst());
            ICustomer.Gender gender = switch (row.get(1)) {
                case "Frau" -> ICustomer.Gender.W;
                case "Herr" -> ICustomer.Gender.M;
                default -> ICustomer.Gender.U;
            };
            String firstName = row.get(2);
            String lastName = row.get(3);
            LocalDate birthdate = row.get(4).isEmpty() ? null : LocalDate.parse(row.get(4), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            Customer customer = new Customer(id, gender, firstName, lastName, birthdate);

            CustomerDao dao = new CustomerDao();
            dao.insert(customer);
        });
    }

    private static List<List<String>> readCSV(String fileName, char separator) {

        String dirToCsv = System.getProperty("user.dir") + File.separator + "src" + File.separator + "Test" + File.separator + "resources" + File.separator;
        List<List<String>> result = new ArrayList<>();
        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader(dirToCsv + fileName)).withCSVParser(new CSVParserBuilder().withSeparator(separator).build()).build();
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

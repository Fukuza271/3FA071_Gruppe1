package database;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import database.daos.CustomerDao;
import database.daos.ReadingDao;
import database.daos.UserDao;
import database.entities.Customer;
import database.entities.Reading;
import database.entities.User;
import interfaces.ICustomer;
import interfaces.IReading;
import interfaces.IUser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExampleDataProvider {

    public static ExampleDataProvider shared = new ExampleDataProvider();


    private ExampleDataProvider() {
    }

    public void insertHeizungReadingData() {    //Fügt die daten in die Heizungszählertabelle aus der CSV datei
        insertReadingData(IReading.KindOfMeter.HEIZUNG, readCSV("heizung.csv", ';'));
    }

    public void insertWasserReadingData() {     //Fügt die daten in die Wasserzählertabelle aus der CSV datei
        insertReadingData(IReading.KindOfMeter.WASSER, readCSV("wasser.csv", ';'));
    }

    public void insertStromReadingData() {      //Fügt die daten in die Stromzählertabelle aus der CSV datei
        insertReadingData(IReading.KindOfMeter.STROM, readCSV("strom.csv", ';'));
    }

    private void insertReadingData(IReading.KindOfMeter type, List<List<String>> data) {    //fügt die gegebenen reading daten in die Datenbank ein
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

            if (substitute) {
                Pattern pattern = Pattern.compile("(?<=^Zählertausch:\sneue\sNummer\s).+(?=$)");
                Matcher matcher = pattern.matcher(comment);

                if (matcher.find()) {
                    meterId = matcher.group();
                    meterId.replaceAll("\\s+", "");
                }
            }

            CustomerDao customerDao = new CustomerDao();
            Customer customer = customerDao.findById(customerId);

            (new ReadingDao()).insert(new Reading(UUID.randomUUID(), customer, date, meterId, meterCount, type, comment, substitute));
        }
    }

    public void insertCustomerData() {  //Fügt die gegebenen Kundendaten in die Datenbank ein
        readCSV("kunden_utf8.csv", ',').forEach(row -> {
            String first = row.getFirst();
            if (first.equals("UUID")) {
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

            (new CustomerDao()).insert(new Customer(id, gender, firstName, lastName, birthdate));
        });
    }

    public void insertUserData() {
        readCSV("users.csv", ',').forEach(row -> {
            String first = row.getFirst();
            if (first.equals("UUID")) {
                return;
            }

            UUID id = UUID.fromString(first);

            String username = row.get(1);
            String password = row.get(2);
            IUser.Role role = row.get(3).equals("Admin") ? IUser.Role.Admin : IUser.Role.Customer;
            String customerId = row.get(4);
            Customer customer = !customerId.equals("null") ? new CustomerDao().findById(UUID.fromString(customerId)) : null;

            (new UserDao()).insert(new User(id, username, password, role, customer));
        });
    }

    private List<List<String>> readCSV(String fileName, char separator) {   //Liest die beigefügten CSV dateien

        String dirToCsv = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
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

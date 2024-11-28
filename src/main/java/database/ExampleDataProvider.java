package database;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import database.daos.CustomerDao;
import database.daos.ReadingDao;
import database.entities.Customer;
import database.entities.Reading;
import interfaces.ICustomer;
import interfaces.IReading;

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

    public void insertHeizungReadingData() {
        insertReadingData(IReading.KindOfMeter.HEIZUNG, readCSV("heizung.csv", ';'));
    }

    public void insertWasserReadingData() {
        insertReadingData(IReading.KindOfMeter.WASSER, readCSV("wasser.csv", ';'));
    }

    public void insertStromReadingData() {
        insertReadingData(IReading.KindOfMeter.STROM, readCSV("strom.csv", ';'));
    }

    private void insertReadingData(IReading.KindOfMeter type, List<List<String>> data) {
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
                Matcher matcher = pattern.matcher("Zählertausch: neue Nummer Xr-2021-2312434xz");
                boolean matchFound = matcher.find();

//                System.out.println(matchFound);
//                System.out.println(matcher.group());

                meterId = matcher.group();
                meterId.replaceAll("\\s+","");
            }

            (new ReadingDao()).insert(new Reading(UUID.randomUUID(), customerId, date, meterId, meterCount, type, comment, substitute));
        }
    }

    public void insertCustomerData() {
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

            (new CustomerDao()).insert(new Customer(id, gender, firstName, lastName, birthdate));
        });
    }

    private List<List<String>> readCSV(String fileName, char separator) {

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

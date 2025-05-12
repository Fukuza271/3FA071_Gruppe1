package database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import database.daos.CustomerDao;
import database.entities.Customer;
import org.checkerframework.checker.units.qual.C;
import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportExportParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Customer> importJsonCustomer(File jsonFile) throws IOException {
        return objectMapper.readValue(jsonFile, new TypeReference<List<Customer>>() {});
    }

    public static void exportJSONCustomer(File outputFile) {
        try {
            CustomerDao customerDao = new CustomerDao();
            List<Customer> customers = customerDao.findAll();

            // Gson für Pretty-Printing (übersichtliche Formatierung)
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            // JSON in Datei schreiben
            try (FileWriter writer = new FileWriter(outputFile)) {
                gson.toJson(customers, writer);
                System.out.println("Kundendaten erfolgreich exportiert nach: " + outputFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Export: " + e.getMessage());
        }
    }

    public static void exportJSONReading() {

    }
    public static void exportXMLCustomer() {

    }

    public static void exportXMLReading() {

    }
    public static void exportCSVCustomer() {

    }

    public static void exportCSVReading() {

    }
}

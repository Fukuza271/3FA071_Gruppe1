package database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.entities.Customer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImportExportParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Customer> importJsonCustomer(File jsonFile) throws IOException {
        return objectMapper.readValue(jsonFile, new TypeReference<List<Customer>>() {});
    }

    public static void exportJSONCustomer() {

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

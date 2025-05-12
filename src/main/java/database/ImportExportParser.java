package database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import database.daos.CustomerDao;
import database.entities.Customer;
import org.checkerframework.checker.units.qual.C;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportExportParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Customer> importJsonCustomer(File jsonFile) throws IOException {
        return objectMapper.readValue(jsonFile, new TypeReference<List<Customer>>() {});
    }

    public static void exportJSONCustomer() {
        CustomerDao customerDao = new CustomerDao();
        List<Customer> customers = customerDao.findAll();
        Gson gson = new Gson();
        List<String> formatList = new ArrayList<>();
        for (Customer customer: customers) {
            formatList.add(gson.toJson(customer));
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

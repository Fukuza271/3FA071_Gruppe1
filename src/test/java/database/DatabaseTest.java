package database;

import database.daos.CustomerDao;
import database.daos.ReadingDao;
import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class DatabaseTest {
    protected static IDatabaseConnection connection;
    protected static CustomerDao customerDao;
    protected static ReadingDao readingDao;

    @BeforeAll
    public static void beforeAll() {
        customerDao = new CustomerDao();
        readingDao = new ReadingDao();
    }

    @AfterAll
    public static void afterAll() {
        connection.closeConnection();
    }

    @BeforeEach
    public void setup() {
        connection = (new DatabaseConnection()).openConnection(Property.readTestProperties());
        connection.createAllTables();
    }
}

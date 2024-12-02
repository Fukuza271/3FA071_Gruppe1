package database;

import database.daos.CustomerDao;
import database.daos.ReadingDao;
import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class DatabaseTest {
    protected static IDatabaseConnection connection;
    protected static CustomerDao customerDao;
    protected static ReadingDao readingDao;

    @BeforeAll
    public static void beforeAll() {
        connection = (new DatabaseConnection()).openConnection(Property.readTestProperties());
        customerDao = new CustomerDao();
        readingDao = new ReadingDao();
    }

    @BeforeEach
    public void setup() {
        connection.createAllTables();
    }
}

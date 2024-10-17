package Database_Tests;

import database.DatabaseConnection;
import database.daos.CustomerDao;
import database.daos.ReadingDao;
import interfaces.IDatabaseConnection;
import org.junit.jupiter.api.AfterAll;

public abstract class BasicTests {
    
    static IDatabaseConnection connection = new DatabaseConnection();
    static CustomerDao customerDao = new CustomerDao();
    static ReadingDao readingDao = new ReadingDao();
}

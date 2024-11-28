package Database_Tests;

import database.ExampleDataProvider;
import org.junit.jupiter.api.Test;

public class ExampleDataProviderTest {

    @Test
    public void insertHeizungReadingDataTest() {
        ExampleDataProvider.shared.insertHeizungReadingData();

        System.out.println("Reading added");
    }

    @Test
    public void insertWasserReadingDataTest() {
        ExampleDataProvider.shared.insertWasserReadingData();

        System.out.println("Reading added");
    }

    @Test
    public void insertStromReadingDataTest() {
        ExampleDataProvider.shared.insertStromReadingData();

        System.out.println("Reading added");
    }

    @Test
    public void insertCustomerData() {
        ExampleDataProvider.shared.insertCustomerData();

        System.out.println("Customer added");
    }

}

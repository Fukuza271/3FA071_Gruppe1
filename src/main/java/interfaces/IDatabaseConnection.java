package interfaces;

import java.sql.Connection;
import java.util.Properties;

public interface IDatabaseConnection {

    public IDatabaseConnection openConnection(Properties properties);

    public void createAllTables();

    public void truncateAllTables();

    public void removeAllTables();

    public void closeConnection();

    //Todo: Fragen ob wir das hier hinzufügen dürfen
    public Connection getConnection();
}

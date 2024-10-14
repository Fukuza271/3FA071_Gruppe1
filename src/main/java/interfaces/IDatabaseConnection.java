package interfaces;

import org.mariadb.jdbc.Connection;

import java.util.Properties;

public interface IDatabaseConnection {

    public IDatabaseConnection openConnection(Properties properties);

    public void createAllTables();

    public void truncateAllTables();

    public void removeAllTables();

    public void closeConnection();
}

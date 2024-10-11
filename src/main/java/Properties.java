import Konstanten.Constants;
import database.DatabaseConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Properties {

    public java.util.Properties readProperties() {
        // Systemnutzer auslesen
        String systemnutzer = Constants.SYSTEMNUTZER;
        // Home-Verzeichnis des Nutzers
        String homeDir = System.getProperty("user.dir");
        String fileName = "database.properties";
        String filePath = homeDir + "\\src\\main\\resources\\" + fileName;
        java.util.Properties properties = new java.util.Properties();
        File propertiesFile = new File(filePath);
        System.out.println(filePath);

        try {
            FileInputStream input = new FileInputStream(filePath);
            properties.load(input);

            // Ersetze "systemnutzer" durch den tatsächlichen Systemnutzer im Schlüssel
//            String dbUrl = properties.getProperty("systemnutzer.db.url").replace("systemnutzer", systemnutzer);
//            String dbUser = properties.getProperty("systemnutzer.db.user").replace("systemnutzer", systemnutzer);
//            String dbPassword = properties.getProperty("systemnutzer.db.pw").replace("systemnutzer", systemnutzer);

            // Werte basierend auf dem Systemnutzer laden
            String dbUrl = properties.getProperty(systemnutzer + ".db.url");
            String dbUser = properties.getProperty(systemnutzer + ".db.user");
            String dbPassword = properties.getProperty(systemnutzer + ".db.pw");
            System.out.println("Datenbank-URL: " + dbUrl);
            System.out.println("Benutzer: " + dbUser);
            System.out.println("Passwort: " + dbPassword);

        } catch (IOException e) {
            e.printStackTrace();
        }
        DatabaseConnection connection = new DatabaseConnection();
        return properties;
    }
}

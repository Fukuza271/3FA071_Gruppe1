import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {

    public static java.util.Properties readProperties() {
        // Systemnutzer auslesen
        String systemnutzer = System.getProperty("user.name");
        // Home-Verzeichnis des Nutzers
        String homeDir = System.getProperty("user.dir");
        String fileName = "database.properties";
        String filePath = homeDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + fileName;
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream(filePath);
            properties.load(input);
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

        return properties;
    }
}

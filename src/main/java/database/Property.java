package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {

    public static Properties readProperties() { //Gibt die Eigenschaften zurück

        return getProperties(false);
    }

    public static Properties readTestProperties() { //Gibt die Testeigenschaften zurück
        return getProperties(true);
    }

    private static Properties getProperties(boolean useTestProperties) {    //Holt die Eigenschafen aus der CSV Datei ausser wenn useTestProperties wahr ist
        String homeDir = System.getProperty("user.dir");
        String fileName = "database.properties";
        String filePath = String.format("%s%ssrc%s%s%sresources%s%s", homeDir, File.separator, File.separator, useTestProperties ? "test" : "main", File.separator, File.separator, fileName);
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream(filePath);
            properties.load(input);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return properties;
    }
}
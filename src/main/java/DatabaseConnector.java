import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author postgresqltutorial.com
 */
public class DatabaseConnector {
    // url = jdbc:postgresql://<database_host>:<port>/<database_name>

    /**
     * Connect to the PostgreSQL database
     *
     * @return a Connection object
     */
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(fileInputStream);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Connection connect() {
        Properties properties = loadProperties();
        if (properties == null) {
            System.out.println("Failed to load properties.");
            return null;
        }

        String dbUrl = properties.getProperty("db.url");
        String dbUsername = properties.getProperty("db.username");
        String dbPassword = properties.getProperty("db.password");

        try {
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

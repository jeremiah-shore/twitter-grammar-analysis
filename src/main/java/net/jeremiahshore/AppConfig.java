package net.jeremiahshore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {
    private static final String APPCONFIG_PROPERTIES_FILEPATH =
            AppConfig.class.getClassLoader().getResource("appconfig.properties").getPath();

    static final String CONSUMER_API_KEY = getProperty("CONSUMER_API_KEY");
    static final String CONSUMER_API_SECRET_KEY = getProperty("CONSUMER_API_SECRET_KEY");
    static final String ACCESS_TOKEN = getProperty("ACCESS_TOKEN");
    static final String ACCESS_TOKEN_SECRET = getProperty("ACCESS_TOKEN_SECRET");

    public static final String TWEET_DATA_FILE = getProperty("TWEET_DATA_FILE");

    static String getProperty(String key) {
        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(APPCONFIG_PROPERTIES_FILEPATH);
            properties.load(fis);
        } catch (FileNotFoundException e) {
            System.out.println("Properties file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to read from the the application properties file.");
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}

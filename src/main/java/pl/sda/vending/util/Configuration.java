package pl.sda.vending.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private final Properties properties;

    public Configuration() {
        properties = new Properties();
        try {
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        properties.list(System.out);
    }

    public Long getLongProperty(String namePar , Long defaultValue){
        String property = properties.getProperty(namePar);
        if (property == null){
            return defaultValue;
        }
        return Long.parseLong(property);

    }

    public String getStringProperty(String namePar , String defaultValue){
        return properties.getProperty(namePar , defaultValue);
    }
}

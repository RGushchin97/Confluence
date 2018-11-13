package utils;

import java.io.*;
import java.util.Properties;

public class PropertyReader {

    private static Properties properties;
    private static String fileName = "data.properties";
    
    static {
        properties = new Properties();
        InputStream fileStream = null;
        try {
            fileStream = new FileInputStream(PropertyReader.class.getClassLoader().getResource(fileName).getPath());
            properties.load(fileStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}

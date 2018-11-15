package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class FileUtils {

    public static String readFileIntoString(String filePath) {
        BufferedReader reader = null;
        StringBuilder  stringBuilder = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            String ls = System.getProperty("line.separator");
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert reader != null;
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    public static String getResourcePath(String resourceName) {
        return Objects.requireNonNull(FileUtils.class.getClassLoader().getResource(resourceName)).getPath();
    }
}

package utils;

import java.util.List;

public class StringUtils {

    public static String addToStartAndToEnd(String source, String start, String end) {
        return new StringBuilder(start).append(source).append(end).substring(0);
    }

    public static String listToString(List<String> strings) {
        StringBuilder sb = new StringBuilder();
        for(String str : strings) {
            sb.append(str).append(System.getProperty("line.separator"));
        }
        return sb.substring(0).trim();
    }

    public static void addToStartAndToEndInList(List<String> list, String start, String end) {
        for(int i = 0; i < list.size(); i++) {
            list.set(i, addToStartAndToEnd(list.get(i), start, end));
        }
    }
}

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

    public static String listOfRowsToString(List<String[]> rows) {
        StringBuilder sb = new StringBuilder();
        for(String[] row : rows) {
            for(String string : row) {
                sb.append(string);
            }
            sb.append(System.getProperty("line.separator"));
        }
        return sb.substring(0);
    }

    public static void addToStartAndToEndInList(List<String> list, String start, String end) {
        for(int i = 0; i < list.size(); i++) {
            list.set(i, addToStartAndToEnd(list.get(i), start, end));
        }
    }

    public static String[] addToStartAndToEndInArray(String[] source, String start, String end) {
        String[] result = new String[source.length];
        for(int i = 0; i < source.length; i++) {
            result[i] = addToStartAndToEnd(source[i], start, end);
        }
        return result;
    }

    public static void addToStartAndToEndToAllWordsInList(List<String[]> rows, String start, String end) {
        for(int i = 0; i < rows.size(); i++) {
            rows.set(i, addToStartAndToEndInArray(rows.get(i), start, end));
        }
    }

    public static String[] addToStartAndToEndToFirstAndLastInArray(String[] source, String start, String end) {
        int length = source.length;
        String[] result = new String[length];
        String first = new StringBuilder(start).append(source[0]).substring(0);
        String last = new StringBuilder(source[length - 1]).append(end).substring(0);
        result[0] = first;
        result[length - 1] = last;
        for(int i = 1; i < length - 1; i++)  {
            result[i] = source[i];
        }
        return result;
    }

    public static void addToStartAndToEndOfRows(List<String[]> rows, String start, String end) {
        for(int i = 0; i < rows.size(); i++) {
            rows.set(i, addToStartAndToEndToFirstAndLastInArray(rows.get(i), start, end));
        }
    }

    public static String formHtmlTableFromList(List<String[]> rows, List<String> headers) {
        addToStartAndToEndInList(headers, "<th>", "</th>");
        String tableHeaders = listToString(headers);
        tableHeaders = addToStartAndToEnd(tableHeaders, "<tr>", "</tr>");
        addToStartAndToEndToAllWordsInList(rows, "<td>", "</td>");
        addToStartAndToEndOfRows(rows, "<tr>", "</tr>");
        String table = new StringBuilder(tableHeaders).append(listOfRowsToString(rows)).substring(0);
        table = addToStartAndToEnd(table, "<table border=\"1\">", "</table>");
        return table;
    }
}

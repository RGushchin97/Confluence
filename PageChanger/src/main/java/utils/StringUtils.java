package utils;

import java.util.Arrays;
import java.util.List;

public class StringUtils {

    private static String tableStart = "<table border=\"1\">";
    private static String tableEnd = "</table>";
    private static String rowStart = "<tr>";
    private static String rowEnd = "</tr>";
    private static String headerOpening = "<th>";
    private static String headerClosing = "</th>";
    private static String cellOpening = "<td>";
    private static String cellClosing = "</td>";

    private static String makeHtmlRow(String[] elements, String opening, String closing) {
        StringBuilder rowBuilder = new StringBuilder(rowStart);
        Arrays.stream(elements)
                .forEach(head -> {
                    StringBuilder sb = new StringBuilder(opening);
                    sb.append(head).append(closing);
                    rowBuilder.append(sb);
                });
        return rowBuilder.append(rowEnd).toString();
    }

    private static String makeHtmlTableBody(List<String[]> rows) {
        StringBuilder bodyBuilder = new StringBuilder();
        rows.forEach(row -> bodyBuilder.append(makeHtmlRow(row, cellOpening, cellClosing)));
        return bodyBuilder.toString();
    }

    public static String formHtmlTableFromList(List<String[]> rows, String[] headers) {
        String tableHeaders = makeHtmlRow(headers, headerOpening, headerClosing);
        String tableBody = makeHtmlTableBody(rows);
        return tableStart + tableHeaders + tableBody + tableEnd;
    }
}

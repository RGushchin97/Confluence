package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlWorker {

    private String url;
    private String login;
    private String password;
    private ResultSet resultSet;
    private Connection connection;

    public SqlWorker(String url, String login, String password) {
        this.url = url;
        this.login = login;
        this.password = password;
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeQuery(String query) {
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet getResultSet() {
        return resultSet;
    }

    public List<String> getRows() {
        ArrayList<String> rows = new ArrayList<>();
        try {
            ResultSetMetaData metadata = resultSet.getMetaData();
            int columnCount = metadata.getColumnCount();
            while (resultSet.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(resultSet.getString(i)).append(" ");
                }
                rows.add(row.substring(0).trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }
}

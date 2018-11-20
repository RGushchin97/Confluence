package tasks;

import com.google.gson.*;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import requests.RequestsWorker;
import utils.*;

import java.io.*;
import java.util.List;

import static utils.PropertyReader.getProperty;

public class ChangingConfluencePage {

    private static final String PAGE_RESOURCE = "page.json";
    private static final String QUERY_RESOURCE = "confluence_query.sql";
    private static BasicHeader authHeader = new BasicHeader("Authorization",
            PropertyReader.getProperty("authToken"));
    private static BasicHeader contentTypeHeader = new BasicHeader("Content-Type",
            PropertyReader.getProperty("contentType"));
    private static String title = "Humans on projects";
    private static String header1 = "Last name";
    private static String header2 = "First name";
    private static String header3 = "Project";


    public static void main(String[] args) throws FileNotFoundException {
        SqlWorker sqlWorker = new SqlWorker(getProperty("db.url"), getProperty("db.login"), getProperty("db.password"));
        String queryPath = FileUtils.getResourcePath(QUERY_RESOURCE);
        String query = FileUtils.readFileIntoString(queryPath);
        sqlWorker.executeQuery(query);
        List<String[]> rows = sqlWorker.getRows();
        sqlWorker.close();

        String table = StringUtils.formHtmlTableFromList(rows, new String[] { header1, header2, header3 });
        String id = getProperty("id");

        RequestsWorker requestsWorker = new RequestsWorker(getProperty("url") + id);
        requestsWorker.makeGetRequest(new Header[]{ authHeader });
        String responseString = requestsWorker.getResponseString();

        int version = ConfluenceJson.getVersion(responseString);

        String pagePath = FileUtils.getResourcePath(PAGE_RESOURCE);
        JsonObject page = JsonUtils.getJsonObject(new FileReader(pagePath));
        ConfluenceJson.makePage(page, title, table, version, id);
        String body = JsonUtils.elementToJsonString(page);

        requestsWorker.makePutRequest(new Header[] { authHeader, contentTypeHeader }, body);
    }
}

import com.google.gson.*;
import org.apache.http.Header;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import requests.RequestsWorker;
import utils.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static int version;
    private static final String BODY = "body";
    private static final String VALUE = "value";
    private static final String VERSION = "version";
    private static final String STORAGE = "storage";
    private static final String NUMBER = "number";
    private static final String PAGE_RESOURCE = "page.json";
    private static final String QUERY_RESOURCE = "confluence_query.sql";
    private static BasicHeader authHeader = new BasicHeader("Authorization",
            PropertyReader.getProperty("authToken"));
    private static BasicHeader contentTypeHeader = new BasicHeader("Content-Type",
            PropertyReader.getProperty("contentType"));
    private static String header1 = "Last name";
    private static String header2 = "First name";
    private static String header3 = "Project";


    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        SqlWorker sqlWorker = new SqlWorker(PropertyReader.getProperty("db.url"),
                PropertyReader.getProperty("db.login"), PropertyReader.getProperty("db.password"));
        String queryPath = FileUtils.getResourcePath(QUERY_RESOURCE);
        String query = FileUtils.readFileIntoString(queryPath);
        sqlWorker.executeQuery(query);
        List<String[]> rows = sqlWorker.getRows();
        sqlWorker.close();

        ArrayList<String> headers = new ArrayList<>();
        headers.add(header1);
        headers.add(header2);
        headers.add(header3);
        String table = StringUtils.formHtmlTableFromList(rows, headers);

        URIBuilder builder = new URIBuilder(PropertyReader.getProperty("url") + PropertyReader.getProperty("id"));
        RequestsWorker requestsWorker = new RequestsWorker(builder.build());

        requestsWorker.makeGetRequest(new Header[]{ authHeader });
        String responseString = requestsWorker.getResponseString();

        version = JsonUtils.getIntValue(JsonUtils.getAsJson(JsonUtils.getJsonObject(
                new StringReader(responseString)), VERSION), NUMBER);
        String pagePath = FileUtils.getResourcePath(PAGE_RESOURCE);
        JsonObject page = JsonUtils.getJsonObject(new FileReader(pagePath));
        page.getAsJsonObject(VERSION).addProperty(NUMBER, ++version);
        page.getAsJsonObject(BODY).getAsJsonObject(STORAGE).addProperty(VALUE, table);
        String body = JsonUtils.elementToJsonString(page);

        requestsWorker.makePutRequest(new Header[] { authHeader, contentTypeHeader }, body);
    }
}

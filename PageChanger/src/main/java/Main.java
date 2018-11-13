import com.google.gson.*;
import org.apache.http.Header;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;
import requests.RequestsWorker;
import utils.JsonUtils;
import utils.PropertyReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.net.URISyntaxException;

public class Main {

    private static int version;
    private static final String VERSION = "version";
    private static final String NUMBER = "number";
    private static final String pagePath = "src/main/resources/page.json";
    private static BasicHeader authHeader = new BasicHeader("Authorization",
            PropertyReader.getProperty("authToken"));
    private static BasicHeader contentTypeHeader = new BasicHeader("Content-Type",
            PropertyReader.getProperty("contentType"));


    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        URIBuilder builder = new URIBuilder(PropertyReader.getProperty("url") + PropertyReader.getProperty("id"));

        RequestsWorker requestsWorker = new RequestsWorker(builder.build());

        requestsWorker.makeGetRequest(new Header[]{authHeader});
        String responseString = requestsWorker.getResponseString();

        version = JsonUtils.getIntValue(JsonUtils.getAsJson(JsonUtils.getJsonObject(
                new StringReader(responseString)), VERSION), NUMBER);

        JsonElement page = JsonUtils.getJsonObject(new FileReader(pagePath));
        JsonUtils.add(page, VERSION, JsonUtils.formObjectWithIntValue(NUMBER, ++version));

        String body = JsonUtils.elementToJsonString(page);

        requestsWorker.makePutRequest(new Header[] { authHeader, contentTypeHeader}, body);
    }
}

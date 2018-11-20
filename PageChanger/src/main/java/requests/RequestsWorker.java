package requests;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.Header;
import org.apache.http.util.EntityUtils;
import utils.PropertyReader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class RequestsWorker {

    private URI uri;
    private String responseString;

    private String[] headers;

    private CloseableHttpResponse closeableResponse;
    public RequestsWorker(String uri) {
        URIBuilder builder;
        try {
            builder = new URIBuilder(uri);
            this.uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void makeGetRequest(Header[] headers) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeaders(headers);
        try {
            finish(httpclient, httpclient.execute(httpGet));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makePutRequest(Header[] headers, String body) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(uri);
        httpPut.setHeaders(headers);
        httpPut.setEntity(new StringEntity(body, "utf-8"));
        try {
            finish(httpclient, httpclient.execute(httpPut));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finish(CloseableHttpClient httpclient, CloseableHttpResponse execute) throws IOException {
        closeableResponse = execute;
        responseString = getResponseStringFromResponse();
        headers = getHeadersFromResponse();
        closeableResponse.close();
        httpclient.close();
    }


    public String getResponseString() {
        return responseString;
    }

    private String getResponseStringFromResponse() throws IOException {
        HttpEntity entity = closeableResponse.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }

    private String[] getHeadersFromResponse() {
        Header[] headers = closeableResponse.getAllHeaders();
        String[] headersStrings = new String[headers.length];
        for(int i = 0; i < headers.length; i++) {
            headersStrings[i] = headers[i].getName() + headers[i].getValue();
        }
        return headersStrings;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String[] getHeaders() {
        return headers;
    }
}

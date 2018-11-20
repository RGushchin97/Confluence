package utils;

import com.google.gson.JsonObject;

import static utils.JsonUtils.*;

import java.io.StringReader;

public class ConfluenceJson {

    private static final String BODY = "body";
    private static final String VALUE = "value";
    private static final String VERSION = "version";
    private static final String STORAGE = "storage";
    private static final String NUMBER = "number";
    private static final String TITLE = "title";
    private static final String ID = "id";

    public static int getVersion(String jsonSource) {
        return JsonUtils.getIntValue
                (getAsJson(getJsonObject(new StringReader(jsonSource)), VERSION), NUMBER);
    }

    private static void setNewVersion(JsonObject page, int version) {
        page.getAsJsonObject(VERSION).addProperty(NUMBER, ++version);
    }

    private static void setStorageValue(JsonObject page, String storageValue) {
        page.getAsJsonObject(BODY).getAsJsonObject(STORAGE).addProperty(VALUE, storageValue);
    }

    private static void setTitle(JsonObject page, String title) {
        page.addProperty(TITLE, title);
    }

    private static void setId(JsonObject page, String id) {
        page.addProperty(ID, id);
    }

    public static void makePage(JsonObject page, String title, String storageValue, int previousVersion, String id) {
        setId(page, id);
        setNewVersion(page, previousVersion);
        setStorageValue(page, storageValue);
        setTitle(page, title);
    }
}

package utils;

import com.google.gson.*;

import java.io.Reader;

public class JsonUtils {

    private static JsonParser parser = new JsonParser();

    public static JsonObject getAsJson(JsonObject object, String key) {
        return object.get(key).getAsJsonObject();
    }

    public static int getIntValue(JsonObject object, String key) {
        return object.get(key).getAsInt();
    }

    public static JsonObject getJsonObject(Reader reader) {
        return parser.parse(reader).getAsJsonObject();
    }

    public static void add(JsonElement source, String key, JsonObject adding) {
        JsonObject jsonObject = source.getAsJsonObject();
        jsonObject.add(key, adding);
    }

    public static String elementToJsonString(JsonElement element) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(element);
    }

    public static JsonObject formObjectWithInteger(String key, int value) {
        JsonObject object = new JsonObject();
        object.addProperty(key, value);
        return object;
    }

    public static JsonObject formObjectWithString(String key, String value) {
        JsonObject object = new JsonObject();
        object.addProperty(key, value);
        return object;
    }
}

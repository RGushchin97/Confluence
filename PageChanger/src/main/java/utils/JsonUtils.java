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

    public static String elementToJsonString(JsonElement element) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(element);
    }

    public static String getPrettyJsonString(JsonElement element) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(element);
    }
}

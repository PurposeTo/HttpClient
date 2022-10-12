package utils.formatter;

import org.json.JSONArray;
import org.json.JSONObject;
import response.Response;

import java.util.function.Function;

public class Formatters {

    public static Function<Response, String> asString() {
        return (response) -> response.getBody();
    }

    public static Function<String, JSONObject> toJsonObject() {
        return (str) -> new JSONObject(str);
    }

    public static Function<String, JSONArray> toJsonArray() {
        return (str) -> new JSONArray(str);
    }
}

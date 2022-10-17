package utils.objectStream;

import org.json.JSONArray;
import org.json.JSONObject;
import response.Response;

import java.util.Optional;
import java.util.function.Function;

public class Mappers {

    public static Function<Response, String> asString() {
        return Response::getBody;
    }

    public static Function<String, JSONObject> toJsonObject() {
        return JSONObject::new;
    }

    public static Function<String, JSONArray> toJsonArray() {
        return JSONArray::new;
    }

    public static <T> Function<T, Optional<T>> toOptional() {
        return Optional::of;
    }

    public static <T> Function<T, Optional<T>> toOptionalNullable() {
        return Optional::ofNullable;
    }
}

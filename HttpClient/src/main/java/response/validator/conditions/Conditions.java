package response.validator.conditions;

import lombok.NonNull;
import response.HttpStatusCode;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Conditions {

    public static HttpResponseCondition statusCode(int expectedStatusCode) {
        return statusCode(HttpStatusCode.getByValue(expectedStatusCode));
    }

    public static HttpResponseCondition statusCode(@NonNull HttpStatusCode expectedStatusCode) {
        return new HttpResponseCondition(
                r -> {
                    int actualCode = r.getStatusCode().getValue();
                    return expectedStatusCode.getValue() == actualCode;
                },
                r -> {
                    HttpStatusCode actualCode = r.getStatusCode();
                    return String.format("Expected status code: '%s', actual status code: '%s'", expectedStatusCode, actualCode);
                }
        );
    }

    public static HttpResponseCondition body(@NonNull Predicate<String> bodyPredicate) {
        return new HttpResponseCondition(
                d -> {
                    String body = d.getBody();
                    return bodyPredicate.test(body);
                },
                d -> "Fail to validate response body"
        );
    }

    public static HttpResponseCondition and(@NonNull String log,
                                            @NonNull HttpResponseCondition first,
                                            @NonNull HttpResponseCondition second,
                                            HttpResponseCondition... others) {
        List<HttpResponseCondition> httpResponseConditions = Arrays.asList(first, second);
        httpResponseConditions.addAll(Arrays.stream(others).collect(Collectors.toList()));

        return new HttpResponseCondition(
                d -> httpResponseConditions.stream().allMatch(httpResponseCondition -> httpResponseCondition.test(d)),
                d -> log
        );
    }

    public static HttpResponseCondition or(@NonNull String log,
                                           @NonNull HttpResponseCondition first,
                                           @NonNull HttpResponseCondition second,
                                           HttpResponseCondition... others) {
        List<HttpResponseCondition> httpResponseConditions = Arrays.asList(first, second);
        httpResponseConditions.addAll(Arrays.stream(others).collect(Collectors.toList()));

        return new HttpResponseCondition(
                d -> httpResponseConditions.stream().anyMatch(httpResponseCondition -> httpResponseCondition.test(d)),
                d -> log
        );
    }
}

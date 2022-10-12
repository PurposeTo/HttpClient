package response.validator.conditions;

import lombok.NonNull;
import response.HttpStatusCode;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Conditions {

    public static Condition statusCode(int expectedStatusCode) {
        return statusCode(HttpStatusCode.getByValue(expectedStatusCode));
    }

    public static Condition statusCode(@NonNull HttpStatusCode expectedStatusCode) {
        return new Condition(
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

    public static Condition body(@NonNull Predicate<String> bodyPredicate) {
        return new Condition(
                d -> {
                    String body = d.getBody();
                    return bodyPredicate.test(body);
                },
                d -> "Fail to validate response body"
        );
    }

    public static Condition and(@NonNull String log,
                                @NonNull Condition first,
                                @NonNull Condition second,
                                Condition... others) {
        List<Condition> conditions = Arrays.asList(first, second);
        conditions.addAll(Arrays.stream(others).collect(Collectors.toList()));

        return new Condition(
                d -> conditions.stream().allMatch(condition -> condition.test(d)),
                d -> log
        );
    }

    public static Condition or(@NonNull String log,
                               @NonNull Condition first,
                               @NonNull Condition second,
                               Condition... others) {
        List<Condition> conditions = Arrays.asList(first, second);
        conditions.addAll(Arrays.stream(others).collect(Collectors.toList()));

        return new Condition(
                d -> conditions.stream().anyMatch(condition -> condition.test(d)),
                d -> log
        );
    }
}

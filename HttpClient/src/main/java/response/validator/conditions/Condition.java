package response.validator.conditions;

import exceptions.HttpResponseException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import response.Response;

import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class Condition {
    private final Predicate<Response> predicate;
    private final Function<Response, String> errorReasonGetter;

    public void validate(@NonNull Response response) {
        boolean success = test(response);
        if (success) {
            return;
        }

        String errorReason = errorReasonGetter.apply(response);
        throw new HttpResponseException(response, errorReason);
    }

    public boolean test(@NonNull Response response) {
        return predicate.test(response);
    }
}

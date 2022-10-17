package response.validator.conditions;

import exceptions.HttpResponseException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import response.Response;
import utils.objectStream.Condition;

import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class HttpResponseCondition implements Condition<Response> {
    private final Predicate<Response> predicate;
    private final Function<Response, String> errorReasonGetter;

    @Override
    public void testOrThrow(Response value) {
        boolean success = test(value);
        if (success) {
            return;
        }

        String errorReason = errorReasonGetter.apply(value);
        throw new HttpResponseException(value, errorReason);
    }

    public boolean test(@NonNull Response value) {
        return predicate.test(value);
    }
}

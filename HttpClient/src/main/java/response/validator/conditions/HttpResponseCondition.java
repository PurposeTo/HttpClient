package response.validator.conditions;

import exceptions.HttpResponseException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import response.Response;
import utils.conform.Condition;

import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class HttpResponseCondition implements Condition<Response> {
    private final Predicate<Response> predicate;
    private final Function<Response, String> errorReasonGetter;

    @Override
    public HttpResponseException throwable(Response value) {
        String errorReason = errorReasonGetter.apply(value);
        return new HttpResponseException(value, errorReason);
    }

    public boolean test(@NonNull Response value) {
        return predicate.test(value);
    }
}

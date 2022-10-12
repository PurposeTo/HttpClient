package response.validator;

import response.Response;
import response.validator.conditions.Condition;
import utils.formatter.Formatter;

import java.time.Duration;

public interface ResponseHandler extends Response, Formatter<Response> {
    ResponseHandler validate(Condition condition);

    ResponseHandler retryUntil(Condition condition, Duration delay, Duration timeout);

}

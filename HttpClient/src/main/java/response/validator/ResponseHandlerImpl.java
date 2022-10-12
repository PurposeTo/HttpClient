package response.validator;

import client.HttpClient;
import lombok.NonNull;
import request.Request;
import response.HttpStatusCode;
import response.HttpVersion;
import response.Response;
import response.validator.conditions.Condition;
import utils.Retryer;
import utils.formatter.Formatter;
import utils.formatter.FormatterImpl;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class ResponseHandlerImpl implements ResponseHandler {
    private final HttpClient httpClient;
    private final Request request;

    private final Response response;

    private final Formatter<Response> formatter;

    public ResponseHandlerImpl(@NonNull HttpClient httpClient,
                               @NonNull Request request,
                               @NonNull Response response) {
        this.httpClient = httpClient;
        this.request = request;
        this.response = response;
        formatter = new FormatterImpl<>(this.response);
    }

    public ResponseHandler validate(Condition condition) {
        condition.validate(response);
        return this;
    }

    @Override
    public HttpVersion getVersion() {
        return getResponse().getVersion();
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return getResponse().getStatusCode();
    }

    @Override
    public String getBody() {
        return getResponse().getBody();
    }

    @Override
    public String toString() {
        return getResponse().toString();
    }

    private Response getResponse() {
        return response;
    }

    @Override
    public <R> Formatter<R> format(@NonNull Function<Response, R> func) {
        return formatter.format(func);
    }

    @Override
    public <R> R formatAndGet(@NonNull Function<Response, R> func) {
        return formatter.formatAndGet(func);
    }

    @Override
    public Response get() {
        return response;
    }

    @Override
    public ResponseHandler retryUntil(Condition condition, Duration delay, Duration timeout) {
        AtomicReference<Response> ar = new AtomicReference<>(response);

        new Retryer<Response>(
                () -> httpClient.send(request),
                condition::test,
                delay,
                timeout)
                .initData(response)
                .handleData(ar::set)
                .run();

        validate(condition);
        return new ResponseHandlerImpl(httpClient, request, ar.get());
    }
}

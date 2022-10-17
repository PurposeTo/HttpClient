package response.validator;

import client.HttpClient;
import lombok.NonNull;
import request.Request;
import response.HttpStatusCode;
import response.HttpVersion;
import response.Response;
import utils.objectStream.Condition;
import utils.objectStream.ObjectStream;
import utils.objectStream.ObjectStreamImpl;

import java.util.function.Function;

public class ResponseHandlerImpl implements ResponseHandler {
    private final HttpClient httpClient;
    private final Request request;

    private final Response response;

    public ResponseHandlerImpl(@NonNull HttpClient httpClient,
                               @NonNull Request request,
                               @NonNull Response response) {
        this.httpClient = httpClient;
        this.request = request;
        this.response = response;
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
    public Response get() {
        return response;
    }

    @Override
    public <R> ObjectStream<R> map(@NonNull Function<Response, R> func) {
        return new ObjectStreamImpl<>(func.apply(response));
    }

    @Override
    public <R> R mapAndGet(@NonNull Function<Response, R> func) {
        return map(func).get();
    }

    @Override
    public ObjectStream<Response> shouldBe(@NonNull Condition<Response> condition) {
        condition.testOrThrow(response);
        return this;
    }

// todo   @Override
//    public ResponseHandler retryUntil(Condition condition, Duration delay, Duration timeout) {
//        AtomicReference<Response> ar = new AtomicReference<>(response);
//
//        new Retryer<Response>(
//                () -> httpClient.send(request),
//                condition::test,
//                delay,
//                timeout)
//                .initData(response)
//                .handleData(ar::set)
//                .run();
//
//        validate(condition);
//        return new ResponseHandlerImpl(httpClient, request, ar.get());
//    }
}

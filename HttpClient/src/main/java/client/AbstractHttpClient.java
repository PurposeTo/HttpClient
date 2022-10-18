package client;

import lombok.NonNull;
import lombok.SneakyThrows;
import request.Request;
import response.Response;
import response.validator.ResponseHandler;
import response.validator.ResponseHandlerImpl;

/**
 * Чтобы декораторы http клиента работали корректно, нужно наследоваться от этого класса,
 * а НЕ от интерфейса.
 * Класс определяет формирование ResponseHandler из Response
 */
public abstract class AbstractHttpClient implements HttpClient {

    protected abstract Response sendHttp(Request request);

    @SneakyThrows
    @Override
    public final ResponseHandler send(Request request) {
        Response response = sendHttp(request);
        return createResponseHandler(response);
    }

    private ResponseHandler createResponseHandler(@NonNull Response response) {
        return new ResponseHandlerImpl(response);
    }
}

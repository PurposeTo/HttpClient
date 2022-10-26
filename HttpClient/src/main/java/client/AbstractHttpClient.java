package client;

import lombok.NonNull;
import lombok.SneakyThrows;
import request.Request;
import response.Response;
import utils.conform.Conform;
import utils.conform.ConformImpl;

import java.util.function.Supplier;

/**
 * Чтобы декораторы http клиента работали корректно, нужно наследоваться от этого класса,
 * а НЕ от интерфейса.
 * Класс определяет формирование ResponseHandler из Response
 */
public abstract class AbstractHttpClient implements HttpClient {

    protected abstract Response sendHttp(Request request);

    @SneakyThrows
    @Override
    public final Conform<Response> send(Request request) {
        Supplier<Response> responseSupplier = () -> sendHttp(request);
        Response response = responseSupplier.get();
        return new ConformImpl<>(responseSupplier, response);
    }
}

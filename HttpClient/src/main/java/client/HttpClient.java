package client;


import lombok.NonNull;
import request.Request;
import response.Response;
import response.validator.ResponseHandler;
import response.validator.ResponseHandlerImpl;

/**
 * HttpClient interface
 */
public interface HttpClient {
    ResponseHandler send(Request request);
}

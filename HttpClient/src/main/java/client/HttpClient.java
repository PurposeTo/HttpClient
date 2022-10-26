package client;


import request.Request;
import response.Response;
import utils.conform.Conform;

/**
 * HttpClient interface
 */
public interface HttpClient {
    Conform<Response> send(Request request);
}

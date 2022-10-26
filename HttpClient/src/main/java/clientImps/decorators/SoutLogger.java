package clientImps.decorators;

import client.AbstractHttpClient;
import client.HttpClient;
import request.Request;
import response.Response;

public final class SoutLogger extends AbstractHttpClient {
    private final HttpClient httpClient;

    public SoutLogger(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    protected Response sendHttp(Request request) {
        System.out.printf(
                "Sending http request with %s:\n%s\n",
                httpClient.getClass().getSimpleName(),
                request.toString());
        Response response = httpClient.send(request).get();
        System.out.printf("Response received:\n%s\n", response.toString());
        return response;
    }
}

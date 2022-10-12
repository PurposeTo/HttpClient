package features;

import client.HttpClient;
import lombok.NonNull;

public class AbstractHttpClientTest {
    @NonNull
    private final HttpClient httpClient;

    public AbstractHttpClientTest(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    protected HttpClient getHttpClient() {
        return httpClient;
    }
}

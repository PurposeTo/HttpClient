package features;

import client.HttpClient;
import request.HttpMethod;
import request.Request;
import request.RequestLineBuilder;
import response.validator.conditions.Conditions;
import utils.URIScheme;

public class HttpGetTest extends AbstractHttpClientTest {

    public HttpGetTest(HttpClient httpClient) {
        super(httpClient);
    }

    public void run() {
        HttpClient httpClient = getHttpClient();
        Request request = new RequestLineBuilder(HttpMethod.GET, URIScheme.HTTPS, "jsonplaceholder.typicode.com")
                .setPath("users")
                .buildToRequest();

        httpClient.send(request)
                .shouldBe(Conditions.statusCode(200));
    }
}

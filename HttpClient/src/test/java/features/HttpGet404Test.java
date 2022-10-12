package features;

import client.HttpClient;
import request.HttpMethod;
import request.Request;
import request.RequestLineBuilder;
import response.validator.conditions.Conditions;
import utils.URIScheme;

import java.time.Duration;

public class HttpGet404Test extends AbstractHttpClientTest {

    public HttpGet404Test(HttpClient httpClient) {
        super(httpClient);
    }

    public void run() {
        HttpClient httpClient = getHttpClient();
        Request request = new RequestLineBuilder(HttpMethod.GET, URIScheme.HTTPS, "reqres.in")
                .setPath("api/users/${id}")
                .addPathParams("id", "23")
                .buildToRequest();

        httpClient.send(request)
                .validate(Conditions.statusCode(404));

    }
}

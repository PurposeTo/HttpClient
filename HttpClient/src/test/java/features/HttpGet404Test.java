package features;

import client.HttpClient;
import request.HttpMethod;
import request.Request;
import request.RequestLineBuilder;
import response.validator.conditions.Conditions;
import utils.URIScheme;
import utils.conform.EmptyConform;
import utils.retryer.strategy.Timeout;

import java.time.Duration;

import static utils.conform.Mappers.asString;

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
        EmptyConform.retryUntil(
                        () -> httpClient.send(request),
                        Conditions.statusCode(404),
                        Duration.ofSeconds(3),
                        new Timeout(Duration.ofSeconds(10)))
                .shouldBe(Conditions.statusCode(404))
                .map(asString());
    }
}

package features;

import client.HttpClient;
import request.HttpMethod;
import request.Request;
import request.RequestLineBuilder;
import response.validator.conditions.Conditions;
import utils.URIScheme;

public class HttpPost400Test extends AbstractHttpClientTest {

    public HttpPost400Test(HttpClient httpClient) {
        super(httpClient);
    }

    public void run() {
        HttpClient httpClient = getHttpClient();
        Request request = new RequestLineBuilder(HttpMethod.POST, URIScheme.HTTPS, "reqres.in")
                .setPath("api/register")
                .buildToRequest();

        httpClient.send(request)
                .shouldBe(Conditions.statusCode(400));
    }
}

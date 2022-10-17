package features;

import client.HttpClient;
import request.HttpMethod;
import request.Request;
import request.RequestLineBuilder;
import request.payload.StringReqPayload;
import response.validator.conditions.Conditions;
import utils.URIScheme;

public class HttpPutTest extends AbstractHttpClientTest {

    public HttpPutTest(HttpClient httpClient) {
        super(httpClient);
    }

    public void run() {
        HttpClient httpClient = getHttpClient();
        Request request = new RequestLineBuilder(HttpMethod.PUT, URIScheme.HTTPS, "reqres.in")
                .setPath("api/users/${id}")
                .addPathParams("id", "2")
                .buildToRequest()
                .setBody(new StringReqPayload(getUser()));

        httpClient.send(request)
                .shouldBe(Conditions.statusCode(200));
    }

    private static String getUser() {
        return "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";
    }

}

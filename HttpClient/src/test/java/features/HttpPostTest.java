package features;

import client.HttpClient;
import request.HttpMethod;
import request.Request;
import request.RequestLineBuilder;
import request.payload.StringReqPayload;
import response.validator.conditions.Conditions;
import utils.URIScheme;

import static utils.conform.Mappers.*;

public class HttpPostTest extends AbstractHttpClientTest {

    public HttpPostTest(HttpClient httpClient) {
        super(httpClient);
    }

    public void run() {
        HttpClient httpClient = getHttpClient();
        Request request = new RequestLineBuilder(HttpMethod.POST, URIScheme.HTTPS, "reqres.in")
                .setPath("api/users")
                .buildToRequest()
                .setBody(new StringReqPayload(getUser()));

        var data = httpClient.send(request)
                .retryUntil(Conditions.statusCode(201))
                .map(asString())
                .map(toJsonObject())
                .get();
    }

    private static String getUser() {
        return "{\n" +
                "    \"id\": 11,\n" +
                "    \"name\": \"Rajkumar SM\",\n" +
                "    \"username\": \"stm\",\n" +
                "    \"email\": \"user@testengineer.ru\",\n" +
                "    \"address\": {\n" +
                "      \"street\": \"Gagarina\",\n" +
                "      \"suite\": \"31\",\n" +
                "      \"city\": \"Moscow\",\n" +
                "      \"zipcode\": \"600007\",\n" +
                "      \"geo\": {\n" +
                "        \"lat\": \"10.0000\",\n" +
                "        \"lng\": \"80.0000\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"phone\": \"1-2345-6-7890\",\n" +
                "    \"website\": \"testengineer.ru\",\n" +
                "    \"company\": {\n" +
                "      \"name\": \"testengineer.ru\",\n" +
                "      \"catchPhrase\": \"website for QA engineers\",\n" +
                "      \"bs\": \"real-time tutorials\"\n" +
                "    }\n" +
                "}";
    }

}

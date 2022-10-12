import client.HttpClient;
import clientImps.apache.ApacheClient;
import clientImps.decorators.SoutLogger;
import features.*;

public class Program {
    public static void main(String[] args) {
        new HttpGetTest(getApache()).run();
        new HttpGet404Test(getApache()).run();
        new HttpPostTest(getApache()).run();
        new HttpPost400Test(getApache()).run();
        new HttpPutTest(getApache()).run();
        new HttpDeleteTest(getApache()).run();
    }

    public static HttpClient getApache() {
        return decorate(new ApacheClient());
    }

    public static HttpClient decorate(HttpClient httpClient) {
        return new SoutLogger(httpClient);
    }
}

import client.HttpClient;
import clientImps.apache.ApacheClient;
import clientImps.decorators.SoutLogger;
import features.*;
import lombok.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class Program {
    public static void main(String[] args) {
        tetstAn(null);
        tetstAn("");
        tetstAn(" ");
//        new HttpGetTest(getApache()).run();
//        new HttpGet404Test(getApache()).run();
//        new HttpPostTest(getApache()).run();
//        new HttpPost400Test(getApache()).run();
//        new HttpPutTest(getApache()).run();
//        new HttpDeleteTest(getApache()).run();
    }

    public static void tetstAn(@Valid @NotBlank String str) {
        System.out.println("КРЯ");
    }

    public static HttpClient getApache() {
        return decorate(new ApacheClient());
    }

    public static HttpClient decorate(HttpClient httpClient) {
        return new SoutLogger(httpClient);
    }
}

package request;

import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import utils.StringUtils;
import utils.URIScheme;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Setter
@Accessors(chain = true)
public class RequestLineBuilder {
    private String replacementRx;
    private final HttpMethod httpMethod;
    private final URIScheme schema;
    private String userInfo;
    private final String host;
    private Integer port;
    private String path;
    private Map<String, String> pathParams = new HashMap<>(); //pathParams - параметры ПУТИ запроса, например "GET .../document/${docId}"
    private Map<String, String> query = new HashMap<>(); // query - параметры запроса, которые передаются после знака "?"
    private String fragment;


    public RequestLineBuilder(@NonNull HttpMethod httpMethod, @NonNull URIScheme schema, @NonNull String host) {
        this.httpMethod = httpMethod;
        this.schema = schema;
        this.host = host;
    }

    public RequestLineBuilder addPathParams(@NonNull String key, String value) {
        pathParams.put(key, value);
        return this;
    }

    public RequestLineBuilder addQueryParams(@NonNull String key, String value) {
        query.put(key, value);
        return this;
    }

    public ReqLine build() {
        URI uri = new UriBuilder(schema, host)
                .setReplacementRx(replacementRx)
                .setUserInfo(userInfo)
                .setPort(port)
                .setPath(path)
                .setPathParams(pathParams)
                .setQuery(query)
                .setFragment(fragment)
                .build();
        return new ReqLine(httpMethod, uri);
    }

    public Request buildToRequest() {
        ReqLine reqLine = build();
        return new Request(reqLine);
    }

}

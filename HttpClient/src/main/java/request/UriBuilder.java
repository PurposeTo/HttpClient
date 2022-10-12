package request;

import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import utils.StringUtils;
import utils.URIScheme;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.stream.Collectors;

/**
 * The URI generic syntax consists of components organized hierarchically in order of decreasing significance from left to right:[26]
 * URI = scheme ":" ["//" authority] path ["?" query] ["#" fragment]
 * <p>
 * The authority component consists of subcomponents:
 * authority = [userinfo "@"] host [":" port]
 */

@Setter
@Accessors(chain = true)
public class UriBuilder {
    private String replacementRx = StringUtils.DEFAULT_REPLACEMENT_RX;

    private final String schema;
    private String userInfo;
    private final String host;
    private int port = -1; // -1 ==> undefined
    private String path;
    private Map<String, String> pathParams = new HashMap<>(); //pathParams - параметры ПУТИ запроса, например "GET .../document/${docId}"
    private Map<String, String> query = new HashMap<>(); // query - параметры запроса, которые передаются после знака "?"
    private String fragment;

    public UriBuilder(@NonNull URIScheme schema, @NonNull String host) {
        this(schema.getValue(), host);
    }

    public UriBuilder(@NonNull String schema, @NonNull String host) {
        this.schema = schema;
        this.host = host;
    }

    public UriBuilder setReplacementRx(String replacementRx) {
        if (StringUtils.isNonNullOrEmpty(replacementRx)) {
            this.replacementRx = replacementRx;
        }

        return this;
    }

    public UriBuilder setPort(Integer port) {
        if (port != null) {
            this.port = port;
        }

        return this;
    }


    public UriBuilder addPathParams(@NonNull String key, String value) {
        pathParams.put(key, value);
        return this;
    }

    public UriBuilder addQueryParams(@NonNull String key, String value) {
        query.put(key, value);
        return this;
    }

    @SneakyThrows
    public URI build() {
        String parametrizedPath = getParametrizedPath();
        String parametrizedQuery = getParametrizedQueryParams();

        return new URI(schema, userInfo, host, port, parametrizedPath, parametrizedQuery, fragment);
    }

    private String getParametrizedPath() {
        if (StringUtils.isNullOrEmpty(path)) {
            return path;
        }

        String parametrizedPath = StringUtils.parametrize(replacementRx, path, pathParams);
        // Если путь не начинается со слеша, то добавить слеш
        if (StringUtils.isNonNullOrEmpty(parametrizedPath) && !(parametrizedPath.startsWith("/"))) {
            parametrizedPath = "/" + parametrizedPath;
        }

        if (StringUtils.containsPattern(parametrizedPath, replacementRx)) {
            String error = String.format("Path must be parametrized completely! Path: %s", parametrizedPath);
            throw new MissingFormatArgumentException(error);
        }
        return parametrizedPath;
    }

    private String getParametrizedQueryParams() {
        if (query.isEmpty()) {
            return null;
        }

        String parametrizedQuery = this.query
                .entrySet()
                .stream()
                .map((e) -> String.format("%s=%s", e.getKey(), e.getValue()))
                .collect(Collectors.joining("&"));

        if (StringUtils.containsPattern(parametrizedQuery, replacementRx)) {
            String error = String.format("Query must be parametrized completely! Query: %s", parametrizedQuery);
            throw new MissingFormatArgumentException(error);
        }
        return parametrizedQuery;
    }
}

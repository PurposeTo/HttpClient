package headers;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Все возможные названия http заголовков
 */
@AllArgsConstructor
@Getter
public enum HeadersEnum {

    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    ACCEPT_RANGES("Accept-Ranges"),
    /**
     * The CORS {@code Access-Control-Allow-Credentials} response header field name.
     */
    ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials"),
    /**
     * The CORS {@code Access-Control-Allow-Headers} response header field name.
     */
    ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"),
    /**
     * The CORS {@code Access-Control-Allow-Methods} response header field name.
     */
    ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"),
    /**
     * The CORS {@code Access-Control-Allow-Origin} response header field name.
     */
    ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
    /**
     * The CORS {@code Access-Control-Expose-Headers} response header field name.
     */
    ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers"),
    /**
     * The CORS {@code Access-Control-Max-Age} response header field name.
     */
    ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age"),
    /**
     * The CORS {@code Access-Control-Request-Headers} request header field name.
     */
    ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers"),
    /**
     * The CORS {@code Access-Control-Request-Method} request header field name.
     */
    ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),
    AGE("Age"),
    ALLOW("Allow"),
    AUTHORIZATION("Authorization"),
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    CONTENT_ENCODING("Content-Encoding"),
    /**
     * The HTTP {@code Content-Disposition} header field name.
     */
    CONTENT_DISPOSITION("Content-Disposition"),
    CONTENT_LANGUAGE("Content-Language"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_LOCATION("Content-Location"),
    CONTENT_MD5("Content-MD5"),
    CONTENT_RANGE("Content-Range"),
    CONTENT_TYPE("Content-Type"),
    /**
     * The HTTP {@code Cookie} header field name.
     */
    COOKIE("Cookie"),
    DATE("Date"),
    DAV("Dav"),
    DEPTH("Depth"),
    DESTINATION("Destination"),
    ETAG("ETag"),
    EXPECT("Expect"),
    EXPIRES("Expires"),
    FROM("From"),
    HOST("Host"),
    IF("If"),
    IF_MATCH("If-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    KEEP_ALIVE("Keep-Alive"),
    LAST_MODIFIED("Last-Modified"),
    /**
     * The HTTP {@code Link} header field name.
     */
    LINK("Link"),
    LOCATION("Location"),
    LOCK_TOKEN("Lock-Token"),
    MAX_FORWARDS("Max-Forwards"),
    OVERWRITE("Overwrite"),
    PRAGMA("Pragma"),
    PROXY_AUTHENTICATE("Proxy-Authenticate"),
    PROXY_AUTHORIZATION("Proxy-Authorization"),
    RANGE("Range"),
    REFERER("Referer"),
    RETRY_AFTER("Retry-After"),
    SERVER("Server"),
    STATUS_URI("Status-URI"),
    /**
     * The HTTP {@code Set-Cookie} header field name.
     */
    SET_COOKIE("Set-Cookie"),
    TE("TE"),
    TIMEOUT("Timeout"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    UPGRADE("Upgrade"),
    USER_AGENT("User-Agent"),
    VARY("Vary"),
    VIA("Via"),
    WARNING("Warning"),
    WWW_AUTHENTICATE("WWW-Authenticate");

    private final String value;
}

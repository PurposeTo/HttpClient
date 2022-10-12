package request;


import headers.Headers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import request.payload.ReqPayload;
import request.payload.StringReqPayload;
import utils.Copyable;
import utils.StringUtils;

/**
 * Описывает параметры http запроса.
 */
public class Request implements Copyable<Request> {

    @Getter
    private final ReqLine reqLine;
    @Getter
    private final Headers headers;
    @Getter
    private ReqPayload body;

    public Request(@NonNull ReqLine reqLine) {
        this(reqLine, new Headers(), new StringReqPayload(""));
    }

    public Request(@NonNull ReqLine reqLine, @NonNull Headers headers, @NonNull ReqPayload body) {
        this.reqLine = reqLine;
        this.headers = headers;
        this.body = body;
    }

    public Request addHeader(@NonNull String key, String value) {
        headers.add(key, value);
        return this;
    }

    public Request setHeader(@NonNull String key, String value) {
        headers.set(key, value);
        return this;
    }

    public Request removeHeader(@NonNull String key) {
        headers.remove(key);
        return this;
    }

    public Request setBody(ReqPayload body) {
        this.body = body;
        return this;
    }

    @Override
    public String toString() {
        String reqLineStr = reqLine.toString();
        String headersStr = headers.isEmpty()
                ? ""
                : "\n" + headers.toString();
        String bodyStr = body == null || StringUtils.isNullOrEmpty(body.toString())
                ? ""
                : "\n\n" + body;

        return reqLineStr + headersStr + bodyStr;
    }

    @Override
    public Request copy() {
        return new Request(this.reqLine.copy(), this.headers.copy(), this.body.copy());
    }
}

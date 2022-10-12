package response;

import headers.Headers;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import utils.StringUtils;

@Accessors(chain = true)
public class ResponseImpl implements Response {

    private final StatusLine statusLine;
    @Setter
    private Headers headers = new Headers();
    @Setter
    private String body;

    public ResponseImpl(@NonNull StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    @Override
    public HttpVersion getVersion() {
        return statusLine.getVersion();
    }

    @Override
    public HttpStatusCode getStatusCode() {
        return statusLine.getStatusCode();
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        String resLineStr = statusLine.toString();
        String headersStr = headers.isEmpty()
                ? ""
                : "\n" + headers.toString();
        String bodyStr = body == null || StringUtils.isNullOrEmpty(body)
                ? ""
                : "\n\n" + body;

        return resLineStr + headersStr + bodyStr;
    }
}

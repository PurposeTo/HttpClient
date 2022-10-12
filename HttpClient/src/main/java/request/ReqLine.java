package request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import utils.Copyable;

import java.net.URI;

@Getter
@RequiredArgsConstructor
public class ReqLine implements Copyable<ReqLine> {
    private final HttpMethod httpMethod;
    private final URI uri;

    @Override
    public String toString() {
        return httpMethod.name() + " " + uri.toString();
    }

    @Override
    @SneakyThrows
    public ReqLine copy() {
        return new ReqLine(this.httpMethod, new URI(this.uri.toString()));
    }
}

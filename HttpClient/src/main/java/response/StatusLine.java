package response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StatusLine {
    private final ProtocolVersion version;
    private final HttpStatusCode statusCode;

    @Override
    public String toString() {
        return version.toString() + " " + statusCode.toString();
    }
}

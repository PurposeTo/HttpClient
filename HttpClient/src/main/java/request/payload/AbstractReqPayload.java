package request.payload;

import lombok.Getter;
import lombok.NonNull;
import utils.ContentType;

import java.nio.charset.Charset;

@Getter
public abstract class AbstractReqPayload<T> implements ReqPayload {
    @NonNull
    private final ContentType contentType; // заголовок Content-Type. Зависит напрямую от данных, поэтому содержится здесь.

    @NonNull
    private final T content;

    protected AbstractReqPayload(@NonNull T content, final String contentType) {
        this(content, ContentType.parse(contentType));
    }

    protected AbstractReqPayload(@NonNull T content, @NonNull final ContentType contentType) {
        this.content = content;
        this.contentType = contentType;
    }

    @Override
    public Charset getCharset() {
        return contentType.getCharset();
    }
}

package request.payload;

import lombok.Getter;
import lombok.NonNull;
import request.payload.visitor.PayloadVisitor;
import utils.ContentType;

import java.nio.charset.Charset;

@Getter
public class StringReqPayload extends AbstractReqPayload<String> {

    public StringReqPayload(@NonNull final String content, @NonNull final ContentType contentType) {
        super(content, contentType);
    }

    public StringReqPayload(@NonNull String content) {
        this(content, ContentType.DEFAULT_TEXT);
    }

    @Override
    public String toString() {
        return getContent();
    }

    @Override
    public void accept(PayloadVisitor visitor) {
        visitor.visitStringPayload(this);
    }

    public byte[] getBytes() {
        Charset charset = getCharset();
        return getContent().getBytes(charset);
    }

    @Override
    public StringReqPayload copy() {
        return new StringReqPayload(getContent(), this.getContentType().copy());
    }
}

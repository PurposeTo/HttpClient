package request.payload;

import lombok.Getter;
import lombok.NonNull;
import request.payload.visitor.PayloadVisitor;
import utils.ContentType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

@Getter
public class BytesReqPayload extends AbstractReqPayload<byte[]> {


    public BytesReqPayload(@NonNull final byte[] content, @NonNull final ContentType contentType) {
        super(content, contentType);
    }

    public BytesReqPayload(@NonNull byte[] content) {
        this(content, ContentType.DEFAULT_BINARY);
    }

    @Override
    public String toString() {
        return Arrays.toString(getContent());
    }

    @Override
    public void accept(PayloadVisitor visitor) {
        visitor.visitBytesPayload(this);
    }

    @Override
    public BytesReqPayload copy() {
        return new BytesReqPayload(this.getContent(), this.getContentType().copy());
    }
}

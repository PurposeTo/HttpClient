package request.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import request.payload.visitor.PayloadVisitor;
import utils.ContentType;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Getter
public class FileReqPayload extends AbstractReqPayload<File> {


    public FileReqPayload(@NonNull final File content, @NonNull final ContentType contentType) {
        super(content, contentType);
    }

    public FileReqPayload(@NonNull File content) {
        this(content, ContentType.DEFAULT_BINARY);
    }

    @Override
    public String toString() {
        return getContent().getName();
    }

    @Override
    public void accept(PayloadVisitor visitor) {
        visitor.visitFilePayload(this);
    }

    @Override
    public FileReqPayload copy() {
        return new FileReqPayload(getContent(), this.getContentType().copy());
    }
}

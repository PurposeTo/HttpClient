package request.payload.multiPart;

import lombok.Getter;
import lombok.NonNull;
import request.payload.visitor.FormPartVisitor;
import utils.ContentType;

import static headers.HeadersEnum.CONTENT_DISPOSITION;

@Getter
public class BytesFormPart extends AbstractFormPart<byte[]> {

    private final String fileName;

    public BytesFormPart(@NonNull String name,
                         @NonNull String fileName,
                         @NonNull byte[] content,
                         String contentType) {
        this(name, fileName, content, ContentType.parse(contentType));
    }

    public BytesFormPart(@NonNull String name,
                         @NonNull String fileName,
                         @NonNull byte[] content,
                         @NonNull ContentType contentType) {
        super(name, content, contentType);
        this.fileName = fileName;

        addHeader(CONTENT_DISPOSITION.getValue(), String.format("filename=\"%s\"", fileName));
    }

    @Override
    public void accept(FormPartVisitor visitor) {
        visitor.visitBytesFormPart(this);
    }

    @Override
    public FormPart copy() {
        return new BytesFormPart(this.getName(), this.getFileName(), this.getContent(), this.getContentType());
    }
}

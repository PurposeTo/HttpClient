package request.payload.multiPart;

import lombok.Getter;
import lombok.NonNull;
import request.payload.visitor.FormPartVisitor;
import utils.ContentType;
import utils.StringUtils;

import java.io.File;

import static headers.HeadersEnum.CONTENT_DISPOSITION;

@Getter
public class FileFormPart extends AbstractFormPart<File> {

    private final String fileName;

    public FileFormPart(@NonNull String name,
                        @NonNull File content,
                        String contentType) {
        this(name, null, content, ContentType.parse(contentType));
    }

    public FileFormPart(@NonNull String name,
                        String fileName,
                        @NonNull File content,
                        String contentType) {
        this(name, fileName, content, ContentType.parse(contentType));
    }

    public FileFormPart(@NonNull String name,
                        String fileName,
                        @NonNull File content,
                        @NonNull ContentType contentType) {
        super(name, content, contentType);
        this.fileName = StringUtils.isNullOrEmpty(fileName)
                ? content.getName()
                : fileName;

        addHeader(CONTENT_DISPOSITION.getValue(), String.format("filename=\"%s\"", fileName));
    }

    @Override
    public void accept(FormPartVisitor visitor) {
        visitor.visitFileFormPart(this);
    }

    @Override
    public FormPart copy() {
        return new FileFormPart(this.getName(), this.getFileName(), this.getContent(), this.getContentType());
    }
}

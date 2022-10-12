package request.payload.multiPart;

import lombok.Getter;
import lombok.NonNull;
import request.payload.visitor.FormPartVisitor;
import utils.ContentType;

@Getter
public class StringFormPart extends AbstractFormPart<String> {

    public StringFormPart(@NonNull String name,
                          @NonNull String content,
                          String contentType) {
        this(name, content, ContentType.parse(contentType));
    }

    public StringFormPart(@NonNull String name,
                          @NonNull String content,
                          @NonNull ContentType contentType) {
        super(name, content, contentType);
    }

    @Override
    public void accept(FormPartVisitor visitor) {
        visitor.visitStringFormPart(this);
    }

    @Override
    public FormPart copy() {
        return new StringFormPart(this.getName(), this.getContent(), this.getContentType());
    }
}

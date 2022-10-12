package request.payload;

import lombok.Getter;
import lombok.NonNull;
import request.payload.multiPart.AbstractFormPart;
import request.payload.multiPart.FormPart;
import request.payload.multiPart.FormParts;
import request.payload.visitor.PayloadVisitor;
import utils.ContentType;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MultiPartReqPayload extends AbstractReqPayload<FormParts> {

    public MultiPartReqPayload(@NonNull final FormParts content, @NonNull final ContentType contentType) {
        super(content, contentType);
    }

    public MultiPartReqPayload(@NonNull FormParts content) {
        this(content, ContentType.MULTIPART_FORM_DATA);
    }

    public MultiPartReqPayload() {
        this(new FormParts(), ContentType.MULTIPART_FORM_DATA);
    }

    public MultiPartReqPayload addPart(@NonNull FormPart part) {
        getContent().addPart(part);
        return this;
    }

    @Override
    public void accept(PayloadVisitor visitor) {
        visitor.visitMultipartPayload(this);
    }

    @Override
    public MultiPartReqPayload copy() {
        return new MultiPartReqPayload(getContent(), this.getContentType().copy());
    }
}

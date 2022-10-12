package clientImps.apache;

import org.apache.hc.client5.http.entity.mime.ByteArrayBody;
import org.apache.hc.client5.http.entity.mime.ContentBody;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.hc.client5.http.entity.mime.StringBody;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import request.payload.multiPart.BytesFormPart;
import request.payload.multiPart.FileFormPart;
import request.payload.multiPart.StringFormPart;
import request.payload.visitor.FormPartVisitor;

import java.util.Objects;

public class ContentBodyBuilderVisitor implements FormPartVisitor {

    private ContentBody body;

    @Override
    public void visitStringFormPart(StringFormPart part) {
        ContentType contentType = ContentType.parse(part.getContentType().toString());
        body = new StringBody(part.getContent(), contentType);
    }

    @Override
    public void visitBytesFormPart(BytesFormPart part) {
        ContentType contentType = ContentType.parse(part.getContentType().toString());
        body = new ByteArrayBody(part.getContent(), contentType, part.getFileName());
    }

    @Override
    public void visitFileFormPart(FileFormPart part) {
        ContentType contentType = ContentType.parse(part.getContentType().toString());
        body = new FileBody(part.getContent(), contentType, part.getFileName());
    }

    public ContentBody build() {
        return Objects.requireNonNull(body);
    }

}

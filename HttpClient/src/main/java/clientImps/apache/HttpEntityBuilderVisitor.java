package clientImps.apache;

import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.entity.mime.ContentBody;
import org.apache.hc.client5.http.entity.mime.FormBodyPart;
import org.apache.hc.client5.http.entity.mime.FormBodyPartBuilder;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import request.payload.BytesReqPayload;
import request.payload.FileReqPayload;
import request.payload.MultiPartReqPayload;
import request.payload.StringReqPayload;
import request.payload.multiPart.FormPart;
import request.payload.visitor.PayloadVisitor;

import java.util.Objects;

public class HttpEntityBuilderVisitor implements PayloadVisitor {
    private HttpEntity entity;

    @Override
    public void visitStringPayload(StringReqPayload body) {
        entity = EntityBuilder.create()
                .setText(body.getContent())
                .setContentType(ContentType.parse(body.getContentType().toString()))
                .build();
    }

    @Override
    public void visitBytesPayload(BytesReqPayload body) {
        entity = EntityBuilder.create()
                .setBinary(body.getContent())
                .setContentType(ContentType.parse(body.getContentType().toString()))
                .build();
    }

    @Override
    public void visitFilePayload(FileReqPayload body) {
        entity = EntityBuilder.create()
                .setFile(body.getContent())
                .setContentType(ContentType.parse(body.getContentType().toString()))
                .build();
    }

    @Override
    public void visitMultipartPayload(MultiPartReqPayload body) {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .setContentType(ContentType.parse(body.getContentType().toString()));

        for (FormPart part : body.getContent()) {
            var contentBodyBuilderVisitor = new ContentBodyBuilderVisitor();
            part.accept(contentBodyBuilderVisitor);
            ContentBody contentBody = contentBodyBuilderVisitor.build();
            FormBodyPart bodyPart = FormBodyPartBuilder.create(part.getName(), contentBody)
                    .build();
            multipartEntityBuilder = multipartEntityBuilder.addPart(bodyPart);
        }

        entity = multipartEntityBuilder.build();
    }

    public HttpEntity build() {
        return Objects.requireNonNull(entity);
    }
}

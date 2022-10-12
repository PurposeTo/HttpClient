package request.payload;

import request.payload.visitor.PayloadVisitor;
import utils.ContentType;
import utils.Copyable;

import java.nio.charset.Charset;

public interface ReqPayload extends Copyable<ReqPayload> {

    ContentType getContentType();

    Charset getCharset();

    void accept(PayloadVisitor visitor);
}

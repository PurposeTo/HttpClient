package request.payload.multiPart;

import headers.Headers;
import request.payload.visitor.FormPartVisitor;
import utils.ContentType;
import utils.Copyable;

public interface FormPart extends Copyable<FormPart> {

    ContentType getContentType();

    String getName();

    Headers getHeaders();

    void accept(FormPartVisitor visitor);


}

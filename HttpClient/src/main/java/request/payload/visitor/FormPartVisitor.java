package request.payload.visitor;

import request.payload.multiPart.BytesFormPart;
import request.payload.multiPart.FileFormPart;
import request.payload.multiPart.StringFormPart;

public interface FormPartVisitor {
    void visitStringFormPart(StringFormPart part);

    void visitBytesFormPart(BytesFormPart part);

    void visitFileFormPart(FileFormPart part);
}

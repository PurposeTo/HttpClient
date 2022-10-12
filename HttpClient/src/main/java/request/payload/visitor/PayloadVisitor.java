package request.payload.visitor;

import request.payload.BytesReqPayload;
import request.payload.FileReqPayload;
import request.payload.MultiPartReqPayload;
import request.payload.StringReqPayload;

public interface PayloadVisitor {
    void visitStringPayload(StringReqPayload body);

    void visitBytesPayload(BytesReqPayload body);

    void visitFilePayload(FileReqPayload body);

    void visitMultipartPayload(MultiPartReqPayload body);
}

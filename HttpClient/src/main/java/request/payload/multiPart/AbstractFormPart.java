package request.payload.multiPart;

import headers.Header;
import headers.Headers;
import lombok.Getter;
import lombok.NonNull;
import utils.ContentType;
import utils.StringUtils;

import static headers.HeadersEnum.CONTENT_DISPOSITION;

@Getter
public abstract class AbstractFormPart<T> implements FormPart {

    private final String name;  // часть заголовка Content-Disposition.

    @NonNull
    private final ContentType contentType;  // заголовок Content-Type. Зависит напрямую от данных, поэтому содержится здесь.

    private final Headers headers = new Headers();

    @NonNull
    private final T content;

    protected AbstractFormPart(@NonNull String name, @NonNull T content, String contentType) {
        this(name, content, ContentType.parse(contentType));
    }

    protected AbstractFormPart(@NonNull String name, @NonNull T content, @NonNull ContentType contentType) {
        StringUtils.requiredNonBlank(name);
        this.name = name;
        this.contentType = contentType;
        this.content = content;

        Header header = new Header(CONTENT_DISPOSITION.getValue(), "form-data")
                .addValue(String.format("name=\"%s\"", this.name));
        addHeader(header);
    }

    public AbstractFormPart<T> addHeader(@NonNull Header header) {
        headers.add(header);
        return this;
    }

    public AbstractFormPart<T> addHeader(@NonNull String name, @NonNull String value) {
        Header header = new Header(name, value);
        addHeader(header);
        return this;
    }
}

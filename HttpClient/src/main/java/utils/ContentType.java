package utils;


import lombok.Getter;
import org.apache.hc.core5.util.CharArrayBuffer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;

public class ContentType implements Copyable<ContentType> {
    public static final ContentType DEFAULT_TEXT = create("text/plain", StandardCharsets.UTF_8);
    public static final ContentType DEFAULT_BINARY = create("application/octet-stream", (Charset) null);
    public static final ContentType MULTIPART_FORM_DATA = create("multipart/form-data", (Charset) null);
    private static final String CHARSET_ANNOUNCMENT = "; charset=";

    @Getter
    private final String mimeType;
    @Getter
    private final Charset charset;

    private ContentType(final String mimeType, final Charset charset) {
        this.mimeType = mimeType;
        this.charset = charset;
    }

    /**
     * Generates textual representation of this content type which can be used as the value
     * of a {@code Content-Type} header.
     */
    @Override
    public String toString() {
        final CharArrayBuffer buf = new CharArrayBuffer(64);
        buf.append(this.mimeType);
        if (this.charset != null) {
            buf.append("; charset=");
            buf.append(this.charset.name());
        }
        return buf.toString();
    }

    private static boolean valid(final String s) {
        for (int i = 0; i < s.length(); i++) {
            final char ch = s.charAt(i);
            if (ch == '"' || ch == ',' || ch == ';') {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a new instance of {@link ContentType}.
     *
     * @param mimeType MIME type. It may not be {@code null} or empty. It may not contain
     *                 characters {@code <">, <;>, <,>} reserved by the HTTP specification.
     * @param charset  charset.
     * @return content type
     */
    public static ContentType create(final String mimeType, final Charset charset) {
        final String normalizedMimeType = StringUtils.requiredNonNullOrEmpty(mimeType).toLowerCase(Locale.ROOT);
        if (!valid(normalizedMimeType)) {
            throw new IllegalArgumentException("MIME type may not contain reserved characters");
        }
        return new ContentType(normalizedMimeType, charset);
    }

    /**
     * Creates a new instance of {@link ContentType} without a charset.
     *
     * @param mimeType MIME type. It may not be {@code null} or empty. It may not contain
     *                 characters {@code <">, <;>, <,>} reserved by the HTTP specification.
     * @return content type
     */
    public static ContentType create(final String mimeType) {
        return create(mimeType, (Charset) null);
    }

    /**
     * Creates a new instance of {@link ContentType}.
     *
     * @param mimeType MIME type. It may not be {@code null} or empty. It may not contain
     *                 characters {@code <">, <;>, <,>} reserved by the HTTP specification.
     * @param charset  charset. It may not contain characters {@code <">, <;>, <,>} reserved by the HTTP
     *                 specification. This parameter is optional.
     * @return content type
     * @throws UnsupportedCharsetException Thrown when the named charset is not available in
     *                                     this instance of the Java virtual machine
     */
    public static ContentType create(final String mimeType, final String charset) throws UnsupportedCharsetException {
        return create(mimeType, StringUtils.isNonNullOrEmpty(charset) ? Charset.forName(charset) : null);
    }

    /**
     * Parses textual representation of {@code Content-Type} value.
     *
     * @throws UnsupportedCharsetException Thrown when the named charset is not available in
     *                                     this instance of the Java virtual machine
     */
    public static ContentType parse(final String str) throws UnsupportedCharsetException {
        StringUtils.requiredNonNullOrEmpty(str);
        String[] split = str.split(CHARSET_ANNOUNCMENT);
        String mime = split[0];
        Charset charset = null;

        if (split.length == 2) {
            String charsetStr = split[1];
            charset = Charset.forName(charsetStr);
        } else {
            throw new IllegalArgumentException(str);
        }

        return new ContentType(mime, charset);
    }

    /**
     * Creates a new instance with this MIME type and the given Charset.
     *
     * @param charset charset
     * @return a new instance with this MIME type and the given Charset.
     * @since 4.3
     */
    public ContentType withCharset(final Charset charset) {
        return create(this.getMimeType(), charset);
    }

    /**
     * Creates a new instance with this MIME type and the given Charset name.
     *
     * @param charset name
     * @return a new instance with this MIME type and the given Charset name.
     * @throws UnsupportedCharsetException Thrown when the named charset is not available in
     *                                     this instance of the Java virtual machine
     * @since 4.3
     */
    public ContentType withCharset(final String charset) {
        return create(this.getMimeType(), charset);
    }


    public boolean isSameMimeType(final ContentType contentType) {
        return contentType != null && mimeType.equalsIgnoreCase(contentType.getMimeType());
    }

    @Override
    public ContentType copy() {
        return new ContentType(this.mimeType, this.charset);
    }
}

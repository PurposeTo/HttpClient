package response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProtocolVersion {
    /**
     * Name of the protocol.
     */
    private final String protocol;

    /**
     * Major version number of the protocol
     */
    private final int major;

    /**
     * Minor version number of the protocol
     */
    private final int minor;

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProtocolVersion)) {
            return false;
        }
        final ProtocolVersion that = (ProtocolVersion) obj;

        return (this.protocol.equals(that.protocol) &&
                (this.major == that.major) &&
                (this.minor == that.minor));
    }

    /**
     * Formats this protocol version as a string.
     *
     * @return a protocol version string, like "HTTP/1.1"
     */
    public String format() {
        return this.protocol +
                '/' +
                this.major +
                '.' +
                this.minor;
    }

    @Override
    public String toString() {
        return format();
    }
}

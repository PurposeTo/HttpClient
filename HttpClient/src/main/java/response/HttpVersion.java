package response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum HttpVersion {
    HTTP_1_0("1."),
    HTTP_1_1("1.1"),
    HTTP_2("2");

    private final String value;

    @Override
    public String toString() {
        return "HTTP/" + value;
    }

    public static HttpVersion getByString(String str) {
        return Arrays.stream(values())
                .filter(value -> value.toString().equals(str))
                .findFirst()
                .orElseThrow();
    }
}

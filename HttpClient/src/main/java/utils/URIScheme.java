package utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum URIScheme {
    FILE("file"),
    HTTP("http"),
    HTTPS("https");

    private final String value;
}

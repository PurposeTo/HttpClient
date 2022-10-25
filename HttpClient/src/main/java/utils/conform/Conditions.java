package utils.conform;

import utils.StringUtils;

import java.util.Objects;

public class Conditions {

    public static <T> Condition<T> nonNull() {
        return Objects::nonNull;
    }

    public static Condition<String> nonBlank() {
        return StringUtils::isNonBlank;
    }
}

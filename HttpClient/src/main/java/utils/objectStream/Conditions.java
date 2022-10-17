package utils.objectStream;

import org.apache.hc.core5.http.NotImplementedException;
import utils.StringUtils;

import java.util.Objects;

public class Conditions {

    public static <T> Condition<T> nonNull() {
        return new Condition<>() {
            @Override
            public void testOrThrow(T value) {
                //todo реализовать через default методы интерфейса
                throw new RuntimeException("Not Implemented");
            }

            @Override
            public boolean test(T value) {
                return Objects.nonNull(value);
            }
        };
    }

    public static Condition<String> nonBlank() {
        return new Condition<>() {
            @Override
            public void testOrThrow(String value) {
                StringUtils.requiredNonBlank(value);
            }

            @Override
            public boolean test(String value) {
                return StringUtils.isNonBlank(value);
            }
        };
    }
}

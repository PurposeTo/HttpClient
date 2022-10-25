package utils.objectStream;

import lombok.NonNull;
import lombok.SneakyThrows;
import response.Response;

import java.util.function.Predicate;

public interface Condition<T> extends Predicate<T> {

    @SneakyThrows
    default void testOrThrow(T value) {
        if (!test(value)) {
            throw throwable(value);
        }
    }

    default Throwable throwable(T value) {
        return new IllegalArgumentException(value.toString());
    }
}

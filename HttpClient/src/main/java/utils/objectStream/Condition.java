package utils.objectStream;

import lombok.NonNull;
import response.Response;

import java.util.function.Predicate;

public interface Condition<T> extends Predicate<T> {
    void testOrThrow(T value);
}

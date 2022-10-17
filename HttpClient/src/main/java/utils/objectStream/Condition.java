package utils.objectStream;

import lombok.NonNull;
import response.Response;

public interface Condition<T> {
    void testOrThrow(T value);

    boolean test(T value);
}

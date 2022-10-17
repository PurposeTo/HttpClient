package utils.objectStream;

import lombok.NonNull;

import java.util.function.Function;

public interface ObjectStream<T> {
    T get();

    <R> ObjectStream<R> map(@NonNull Function<T, R> func);

    <R> R mapAndGet(@NonNull Function<T, R> func);

    ObjectStream<T> shouldBe(@NonNull Condition<T> condition);
}

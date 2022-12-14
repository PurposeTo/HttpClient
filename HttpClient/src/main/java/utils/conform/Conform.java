package utils.conform;

import lombok.NonNull;
import utils.retryer.RetryExecutor;

import java.util.function.Function;

public interface Conform<T> {
    T get();

    <R> Conform<R> map(@NonNull Function<T, R> func);

    <R> R mapAndGet(@NonNull Function<T, R> func);

    Conform<T> shouldBe(@NonNull Condition<T> condition);

    Conform<T> retryUntil(@NonNull Condition<T> condition);

    Conform<T> retryUntil(@NonNull Condition<T> condition, @NonNull RetryExecutor retryExecutor);

    Conform<T> setRetryExecutor(@NonNull RetryExecutor retryExecutor);
}

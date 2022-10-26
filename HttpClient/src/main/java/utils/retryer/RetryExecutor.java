package utils.retryer;

import utils.conform.Condition;

import java.util.function.Supplier;

public interface RetryExecutor {
    <T> T get(Supplier<T> dataGetter, Condition<T> condition);
}

package utils.conform;

import lombok.NonNull;
import utils.retryer.RetryExecutor;
import utils.retryer.RetryExecutorImpl;

import java.util.function.Function;
import java.util.function.Supplier;

public class ConformImpl<T> implements Conform<T> {
    private final T data;
    private final Supplier<T> dataGetter;
    private RetryExecutor retryExecutor = new RetryExecutorImpl();

    public ConformImpl(@NonNull Supplier<T> dataGetter, T data) {
        this.dataGetter = dataGetter;
        this.data = data;
    }

    @Override
    public T get() {
        return data;
    }

    @Override
    public <R> Conform<R> map(@NonNull Function<T, R> func) {
        Supplier<R> newDataGetter = () -> func.apply(data);
        R newData = newDataGetter.get();

        return createConform(newDataGetter, newData);
    }

    @Override
    public <R> R mapAndGet(@NonNull Function<T, R> func) {
        return map(func).get();
    }

    @Override
    public Conform<T> shouldBe(Condition<T> condition) {
        condition.testOrThrow(data);
        return this;
    }

    @Override
    public Conform<T> retryUntil(@NonNull Condition<T> condition) {
        return retryUntil(condition, this.retryExecutor);
    }

    @Override
    public Conform<T> retryUntil(@NonNull Condition<T> condition, @NonNull RetryExecutor retryExecutor) {
        Supplier<T> newDataGetter = () -> retryExecutor.get(this.dataGetter, condition);
        T newData = newDataGetter.get();

        return createConform(newDataGetter, newData);
    }

    @Override
    public Conform<T> setRetryExecutor(@NonNull RetryExecutor retryExecutor) {
        this.retryExecutor = retryExecutor;
        return this;
    }

    private <R> Conform<R> createConform(@NonNull Supplier<R> dataGetter, R data) {
        return new ConformImpl<>(dataGetter, data)
                .setRetryExecutor(this.retryExecutor);
    }
}

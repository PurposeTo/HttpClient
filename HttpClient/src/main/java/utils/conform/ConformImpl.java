package utils.conform;

import lombok.NonNull;

import java.util.function.Function;

public class ConformImpl<T> implements Conform<T> {
    private final T data;

    public ConformImpl(T data) {
        this.data = data;
    }

    @Override
    public T get() {
        return data;
    }

    @Override
    public <R> Conform<R> map(@NonNull Function<T, R> func) {
        R newData = func.apply(data);
        return new ConformImpl<R>(newData);
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
}

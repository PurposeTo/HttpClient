package utils.objectStream;

import lombok.NonNull;

import java.util.function.Function;

public class ObjectStreamImpl<T> implements ObjectStream<T> {
    private final T data;

    public ObjectStreamImpl(T data) {
        this.data = data;
    }

    @Override
    public T get() {
        return data;
    }

    @Override
    public <R> ObjectStream<R> map(@NonNull Function<T, R> func) {
        R newData = func.apply(data);
        return new ObjectStreamImpl<R>(newData);
    }

    @Override
    public <R> R mapAndGet(@NonNull Function<T, R> func) {
        return map(func).get();
    }

    @Override
    public ObjectStream<T> shouldBe(Condition<T> condition) {
        condition.testOrThrow(data);
        return this;
    }
}

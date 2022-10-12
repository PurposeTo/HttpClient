package utils.formatter;

import lombok.NonNull;

import java.util.function.Function;

public class FormatterImpl<T> implements Formatter<T> {
    private final T data;

    public FormatterImpl(T data) {
        this.data = data;
    }

    public <R> FormatterImpl<R> format(@NonNull Function<T, R> func) {
        return new FormatterImpl<R>(func.apply(data));
    }

    public <R> R formatAndGet(@NonNull Function<T, R> func) {
        return format(func).get();
    }

    public T get() {
        return data;
    }
}

package utils.formatter;

import lombok.NonNull;

import java.util.function.Function;

public interface Formatter<T> {

    <R> Formatter<R> format(@NonNull Function<T, R> func);

    <R> R formatAndGet(@NonNull Function<T, R> func);

    T get();
}

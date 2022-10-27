package utils.collector;

import lombok.NonNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


/**
 * Позволяет собрать результаты stream в generic массив.
 * stream.toArray(it -> (E[]) new Object[]{it}); может выбрасывать CastException
 */
public class ArrayCollector<T> implements Collector<T, AtomicReference<T[]>, AtomicReference<T[]>> {

    public static <T> Collector<T, AtomicReference<T[]>, AtomicReference<T[]>> toArrayRef(@NonNull Class<T> clazz) {
        return new ArrayCollector<>(clazz);
    }

    private final Class<T> clazz;

    public ArrayCollector(@NonNull Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Supplier<AtomicReference<T[]>> supplier() {
        return () -> new AtomicReference<>(createArray());
    }

    @Override
    public BiConsumer<AtomicReference<T[]>, T> accumulator() {
        return (arrayRef, element) -> {
            ArrayList<T> arrayList = new ArrayList<T>(Arrays.asList(arrayRef.get()));
            arrayList.add(element);
            arrayRef.set(arrayList.toArray(arrayRef.get()));
        };
    }

    @Override
    public BinaryOperator<AtomicReference<T[]>> combiner() {
        return (arrayRef1, arrayRef2) -> {
            ArrayList<T> arrayList1 = new ArrayList<T>(Arrays.asList(arrayRef1.get()));
            ArrayList<T> arrayList2 = new ArrayList<T>(Arrays.asList(arrayRef2.get()));
            arrayList1.addAll(arrayList2);
            return new AtomicReference<>(arrayList1.toArray(createArray()));
        };
    }

    @Override
    public Function<AtomicReference<T[]>, AtomicReference<T[]>> finisher() {
        return (array) -> array;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }

    private T[] createArray() {
        return (T[]) Array.newInstance(clazz, 0);
    }
}

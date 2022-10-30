package utils.collector;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorsExt {
    public static <T> Collector<T, ?, T> toSingle() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        String error = String.format("Collection must have 1 element, not %s!", list.size());
                        throw new IllegalStateException(error);
                    }
                    return list.get(0);
                }
        );
    }

    public static <T> Collector<T, ?, Optional<T>> toSingleOrEmpty() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() > 1) {
                        String error = String.format("Collection must have 0 or 1 element, not %s!", list.size());
                        throw new IllegalStateException(error);
                    }
                    return list.stream().findFirst();
                }
        );
    }

    public static Collector<Integer, ?, int[]> toPrimitiveInt() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.stream().anyMatch(Objects::isNull)) {
                        throw new IllegalStateException("Collection must not contains null");
                    }

                    return list.stream().mapToInt(it -> it).toArray();
                }
        );
    }
}

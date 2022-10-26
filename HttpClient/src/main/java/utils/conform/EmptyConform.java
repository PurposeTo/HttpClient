package utils.conform;

import java.util.function.Supplier;

public class EmptyConform {
    public static <T> Conform<T> init(Supplier<T> dataGetter) {
        T data = dataGetter.get();
        return new ConformImpl<>(dataGetter, data);
    }

}

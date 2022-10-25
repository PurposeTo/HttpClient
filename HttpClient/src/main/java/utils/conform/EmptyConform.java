package utils.conform;

import lombok.NonNull;
import utils.retryer.Retryer;
import utils.retryer.strategy.Strategies;
import utils.retryer.strategy.Strategy;
import utils.retryer.strategy.Timeout;
import utils.retryer.strategy.ValidData;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class EmptyConform {

    public static <T> Conform<T> init(T data) {
        return new ConformImpl<>(data);
    }

    public static <T> Conform<T> init(Supplier<T> dataGetter) {
        T data = dataGetter.get();
        return init(data);
    }

    private static Strategy DEFAULT_STRATEGY = new Timeout(Duration.ofSeconds(30));

    public static <T> Conform<T> retryUntil(@NonNull Supplier<T> actualDataGetter,
                                            @NonNull Condition<T> condition,
                                            @NonNull Duration delay) {
        return retryUntil(actualDataGetter, condition, delay, DEFAULT_STRATEGY);
    }

    public static <T> Conform<T> retryUntil(@NonNull Supplier<T> actualDataGetter,
                                            @NonNull Condition<T> condition,
                                            @NonNull Duration delay,
                                            @NonNull Strategy strategy) {
        AtomicReference<T> dataAr = new AtomicReference<>();
        Strategy validDataStrategy = new ValidData<>(actualDataGetter, condition)
                .handleData(dataAr::set);
        strategy = Strategies.and(validDataStrategy, strategy);

        new Retryer(delay, strategy)
                .run();

        condition.testOrThrow(dataAr.get());
        return init(dataAr.get());
    }

}

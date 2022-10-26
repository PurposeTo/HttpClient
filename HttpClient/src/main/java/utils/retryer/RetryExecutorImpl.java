package utils.retryer;

import lombok.SneakyThrows;
import utils.conform.Condition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class RetryExecutorImpl implements RetryExecutor {

    private final Duration delay = Duration.ofSeconds(10);

    private final Duration timeout = Duration.ofSeconds(60);


    @Override
    @SneakyThrows
    public <T> T get(Supplier<T> dataGetter, Condition<T> condition) {
        LocalDateTime deadline = LocalDateTime.now().plus(timeout);

        T data = dataGetter.get();
        boolean isDataValid = condition.test(data);

        while (!isDataValid && LocalDateTime.now().isBefore(deadline)) {
            waitDelay();

            try {
                data = dataGetter.get();
                isDataValid = condition.test(data);
            } catch (Throwable ignore) {
                isDataValid = false;
            }
        }

        if (isDataValid) {
            return data;
        } else {
            throw condition.throwable(data);
        }
    }

    @SneakyThrows
    private void waitDelay() {
        Thread.sleep(delay.toMillis());
    }
}

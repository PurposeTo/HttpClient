package utils.retryer;

import lombok.NonNull;
import lombok.SneakyThrows;
import utils.retryer.stopStrategy.Strategy;

import java.time.Duration;
import java.util.function.Consumer;

public class Retryer {
    private final Strategy strategy;

    private final Duration delay; //todo заменить на "OnLoopStrategy"

    private boolean success = false;

    private Consumer<Boolean> resultEvent;


    public Retryer(@NonNull Duration delay, @NonNull Strategy strategy) {
        this.delay = delay;
        this.strategy = strategy;
        this.resultEvent = (r) -> {
        };
    }

    public Retryer handleResult(Consumer<Boolean> result) {
        this.resultEvent = result;
        return this;
    }

    public void run() {
        strategy.onStart();

        while (needRetry()) {
            waitDelay();
            strategy.onLoop();
        }

        onFinish(success);
    }

    /**
     * Соответствуют данные условиям
     */
    private boolean needRetry() {
        success = strategy.needRetry();
        return success;
    }

    private void onFinish(boolean success) {
        resultEvent.accept(success);
    }

    @SneakyThrows
    private void waitDelay() {
        Thread.sleep(delay.toMillis());
    }
}

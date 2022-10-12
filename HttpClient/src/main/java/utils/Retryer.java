package utils;

import lombok.NonNull;
import lombok.SneakyThrows;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Retryer<T> {
    private final Predicate<T> until;
    private final Supplier<T> actualDataGetter;
    private final Duration delay;
    private final Duration timeout;

    private T data;
    private boolean isDataInited = false;
    private boolean success = false;

    private Consumer<T> resultData;
    private Consumer<Boolean> result;


    public Retryer(@NonNull Supplier<T> actualDataGetter,
                   @NonNull Predicate<T> until,
                   @NonNull Duration delay,
                   @NonNull Duration timeout) {
        this.actualDataGetter = actualDataGetter;
        this.until = until;
        this.delay = delay;
        this.timeout = timeout;
        this.resultData = (r) -> {
        };
        this.result = (r) -> {
        };
    }

    public Retryer<T> initData(T data) {
        this.data = data;
        this.isDataInited = true;
        return this;
    }

    public Retryer<T> handleData(Consumer<T> resultData) {
        this.resultData = resultData;
        return this;
    }

    public Retryer<T> handleResult(Consumer<Boolean> result) {
        this.result = result;
        return this;
    }

    public void run() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime deadline = start.plus(timeout);

        if (!isDataInited) {
            initData(getActualData());
        }


        //todo условия можно свернуть в класс abortStrategy
        //пока (Дата НЕ верна , время раньше дедлайна)
        while (!test() && LocalDateTime.now().isBefore(deadline)) {
            waitDelay();
            data = getActualData();
        }
    }

    /**
     * Соответствуют данные условиям
     */
    private boolean test() {
        success = until.test(data);
        handle();
        return success;
    }

    private void handle() {
        resultData.accept(data);
        result.accept(success);
    }

    @SneakyThrows
    private void waitDelay() {
        Thread.sleep(delay.toMillis());
    }

    private T getActualData() {
        return actualDataGetter.get();
    }
}

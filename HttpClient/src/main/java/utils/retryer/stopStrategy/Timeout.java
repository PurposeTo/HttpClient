package utils.retryer.stopStrategy;

import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;

public class Timeout implements Strategy {

    private final Duration timeout;

    private LocalDateTime deadline;

    //todo разбить на 3 класса: ValidData, Timeout, Delay, And
    public Timeout(@NonNull Duration timeout) {
        this.timeout = timeout;
    }


    @Override
    public void onStart() {
        LocalDateTime start = LocalDateTime.now();
        deadline = start.plus(timeout);
    }

    @Override
    public boolean needRetry() {
        return LocalDateTime.now().isBefore(deadline);
    }

    @Override
    public void onLoop() {
    }
}

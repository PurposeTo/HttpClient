package utils.retryer.strategy;

import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDateTime;

public class Timeout implements Strategy {

    private final Duration timeout;

    private LocalDateTime deadline;

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

    @Override
    public void onFinish(boolean success) {

    }
}

package utils.retryer.strategy;

public interface Strategy {
    void onStart();

    boolean needRetry();

    void onLoop();

    void onFinish(boolean success);
}

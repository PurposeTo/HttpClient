package utils.retryer.stopStrategy;

public interface Strategy {
    void onStart();

    boolean needRetry();

    void onLoop();
}

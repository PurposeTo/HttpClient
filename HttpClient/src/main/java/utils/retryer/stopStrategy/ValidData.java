package utils.retryer.stopStrategy;

import lombok.NonNull;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ValidData<T> implements Strategy {

    private final Supplier<T> actualDataGetter;
    private final Predicate<T> validData;

    private Consumer<T> resultDataEvent;

    private T data;
    private boolean isDataInited = false;

    public ValidData(@NonNull Supplier<T> actualDataGetter, @NonNull Predicate<T> isDataValid) {
        this.actualDataGetter = actualDataGetter;
        this.validData = isDataValid;
        this.resultDataEvent = (r) -> {
        };
    }

    public ValidData<T> initData(T data) {
        this.data = data;
        this.isDataInited = true;
        return this;
    }

    public ValidData<T> handleData(Consumer<T> resultData) {
        this.resultDataEvent = resultData;
        return this;
    }

    @Override
    public void onStart() {
        if (!isDataInited) {
            initData(getActualData());
        }
    }

    @Override
    public boolean needRetry() {
        this.data = getActualData();
        return !validData.test(data);
    }

    @Override
    public void onLoop() {
        data = getActualData();
    }

    public void onFinish(boolean success) {
        if (success) {
            resultDataEvent.accept(data);
        }
    }

    private T getActualData() {
        return actualDataGetter.get();
    }

}

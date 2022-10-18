package utils.retryer.strategy;

import java.util.List;

public class Strategies {

    public static Strategy and(Strategy first, Strategy second) {
        List<Strategy> strategies = List.of(first, second);

        return new Strategy() {
            @Override
            public void onStart() {
                strategies.forEach(Strategy::onStart);
            }

            @Override
            public boolean needRetry() {
                return strategies.stream().allMatch(Strategy::needRetry);
            }

            @Override
            public void onLoop() {
                strategies.forEach(Strategy::onLoop);
            }

            @Override
            public void onFinish(boolean success) {
                strategies.forEach(it -> it.onFinish(success));
            }
        };
    }
}

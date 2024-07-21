package AvoidIfElse.StrategyPattern;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentService {
    private final Map<String, PaymentStrategy> paymentStrategies = new HashMap<>();

    public PaymentService(List<PaymentStrategy> strategies) {
        for (PaymentStrategy strategy : strategies) {
            paymentStrategies.put(strategy.getClass().getSimpleName(), strategy);
        }
    }

    public void processPayment(String strategyName, double amount) {
        PaymentStrategy strategy = paymentStrategies.get(strategyName);
        if (strategy != null) {
            strategy.pay(amount);
        } else {
            throw new IllegalArgumentException("No such payment strategy: " + strategyName);
        }
    }
}

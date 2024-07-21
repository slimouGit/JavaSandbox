package Patterns.StrategyPattern;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        // Credit card payment processing logic
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}

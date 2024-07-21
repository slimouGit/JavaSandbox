package Patterns.StrategyPattern;

public class PaypalPayment implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        // PayPal payment processing logic
        System.out.println("Paid " + amount + " using PayPal.");
    }
}

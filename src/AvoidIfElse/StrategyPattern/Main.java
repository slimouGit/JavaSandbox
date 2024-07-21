package AvoidIfElse.StrategyPattern;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        PaymentStrategy creditCardPayment = new CreditCardPayment();
        PaymentStrategy paypalPayment = new PaypalPayment();

        PaymentService paymentService = new PaymentService(Arrays.asList(creditCardPayment, paypalPayment));

        paymentService.processPayment("CreditCardPayment", 150.0);
        paymentService.processPayment("PaypalPayment", 250.0);
    }
}

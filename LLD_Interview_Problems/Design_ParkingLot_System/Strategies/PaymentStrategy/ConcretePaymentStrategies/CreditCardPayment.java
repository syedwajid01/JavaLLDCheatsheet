package LLD_Interview_Problems.Design_ParkingLot_System.Strategies.PaymentStrategy.ConcretePaymentStrategies;

import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.PaymentStrategy.PaymentStrategy;

public class CreditCardPayment implements PaymentStrategy {
    public CreditCardPayment(double fee) {}
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing credit card payment strategy $ " + amount);
    }
}

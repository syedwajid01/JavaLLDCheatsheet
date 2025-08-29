package LLD_Interview_Problems.Design_ParkingLot_System.Strategies.PaymentStrategy.ConcretePaymentStrategies;

import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.PaymentStrategy.PaymentStrategy;

public class CashPayment implements PaymentStrategy {

    public CashPayment(double fee) {
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing cash payment of amount $ " + amount);
    }
}

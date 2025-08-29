package LLD_Interview_Problems.Design_ParkingLot_System;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import LLD_Interview_Problems.Design_ParkingLot_System.Entities.ParkingLot;
import LLD_Interview_Problems.Design_ParkingLot_System.Entities.ParkingSpot;
import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.DurationType;
import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ParkingFeeStrategy;
import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ConcreteFeeStrategy.BasicHourlyFeeStrategy;
import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ConcreteFeeStrategy.PremiumFeeStrategy;
import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.PaymentStrategy.PaymentStrategy;
import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.PaymentStrategy.ConcretePaymentStrategies.CashPayment;
import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.PaymentStrategy.ConcretePaymentStrategies.CreditCardPayment;
import LLD_Interview_Problems.Design_ParkingLot_System.Vehicle.*;

public class Main {
    public static void main(String[] args) {
        List<ParkingSpot> parkingSpots = new ArrayList<>();
        parkingSpots.add(new ParkingSpot(1, VehicleType.CAR));
        parkingSpots.add(new ParkingSpot(2, VehicleType.CAR));
        parkingSpots.add(new ParkingSpot(3, VehicleType.BIKE));
        parkingSpots.add(new ParkingSpot(4, VehicleType.BIKE));

        ParkingLot parkingLot = new ParkingLot(parkingSpots);
        ParkingFeeStrategy basicHourlyFeeStrategy = new BasicHourlyFeeStrategy();
        ParkingFeeStrategy premiumFeeStrategy = new PremiumFeeStrategy();

        // Create vehicles with factory patterns with fee strategy
        Vehicle car1 = VehicleFactory.createVehicle(VehicleType.CAR, "CAR123", basicHourlyFeeStrategy);
        Vehicle bike1 = VehicleFactory.createVehicle(VehicleType.BIKE, "BIKE123", premiumFeeStrategy);

        // Park vehicles
        ParkingSpot carSpot1 = parkingLot.parkVechile(car1);
        ParkingSpot bikeSpot1 = parkingLot.parkVechile(bike1);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Select payment method for you vehicle ");
        System.out.println("1. Cash");
        System.out.println("2. Credit card");
        int paymentMethod = scanner.nextInt();

        if (carSpot1 != null) {
            double carFee = car1.calculateFee(2, DurationType.HOURS);
            PaymentStrategy paymentStrategy = getPaymentStrategy(paymentMethod, carFee);
            paymentStrategy.processPayment(carFee);
            parkingLot.vacateSpot(carSpot1, car1);
        }

        if (bike1 != null) {
            double bikeFee = bike1.calculateFee(2, DurationType.DAYS);
            PaymentStrategy paymentStrategy = getPaymentStrategy(paymentMethod, bikeFee);
            paymentStrategy.processPayment(bikeFee);
            parkingLot.vacateSpot(bikeSpot1, bike1);
        }

        scanner.close();
    }

    private static PaymentStrategy getPaymentStrategy(
            int paymentMethod, double fee) {
        switch (paymentMethod) {
            case 1:
                return new CreditCardPayment(fee);
            case 2:
                return new CashPayment(fee);
            default:
                System.out.println("Invalid choice! Default to Credit card payment.");
                return new CreditCardPayment(fee);
        }
    }
}


/*
Output:

Vehicle is parked successfully at 1
Vehicle is parked successfully at 2
Vehicle is parked successfully at 3
Vehicle is parked successfully at 4
Select payment method for you vehicle 
1. Cash
2. Credit card
1
Processing credit card payment strategy $ 20.0
CAR Vacated the spot 1
Processing credit card payment strategy $ 240.0
BIKE Vacated the spot 3

*/
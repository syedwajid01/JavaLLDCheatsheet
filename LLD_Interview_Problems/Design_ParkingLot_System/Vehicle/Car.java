package LLD_Interview_Problems.Design_ParkingLot_System.Vehicle;

import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ParkingFeeStrategy;

public class Car extends Vehicle {
    public Car(String licenseNumber, ParkingFeeStrategy parkingFeeStrategy) {
        super(licenseNumber, VehicleType.CAR, parkingFeeStrategy);
    }    
}

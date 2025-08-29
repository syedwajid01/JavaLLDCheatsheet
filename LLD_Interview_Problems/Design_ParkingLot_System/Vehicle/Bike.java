package LLD_Interview_Problems.Design_ParkingLot_System.Vehicle;

import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ParkingFeeStrategy;

public class Bike extends Vehicle {
    public Bike(String licenseNumber, ParkingFeeStrategy parkingFeeStrategy) {
        super(licenseNumber, VehicleType.BIKE, parkingFeeStrategy);
    }
}

package LLD_Interview_Problems.Design_ParkingLot_System.Vehicle;

import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ParkingFeeStrategy;

public class Truck extends Vehicle {
    public Truck(String licenseNumber, ParkingFeeStrategy parkingFeeStrategy) {
        super(licenseNumber, VehicleType.TRUCK, parkingFeeStrategy);
    }
}

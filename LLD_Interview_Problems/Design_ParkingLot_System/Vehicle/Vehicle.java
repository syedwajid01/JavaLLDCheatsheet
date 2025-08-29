package LLD_Interview_Problems.Design_ParkingLot_System.Vehicle;

import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.DurationType;
import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ParkingFeeStrategy;

public abstract class Vehicle {
    private String licenseNumber;
    private VehicleType vehicleType;
    private ParkingFeeStrategy parkingFeeStrategy;

    public Vehicle(String licenseNumber, VehicleType vehicleType, ParkingFeeStrategy parkingFeeStrategy) {
        this.licenseNumber = licenseNumber;
        this.vehicleType = vehicleType;
        this.parkingFeeStrategy = parkingFeeStrategy;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public double calculateFee(int duration, DurationType durationType) {
        return parkingFeeStrategy.calculateFee(vehicleType, duration, durationType);
    }
}

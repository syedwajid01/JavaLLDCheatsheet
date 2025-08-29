package LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy;

import LLD_Interview_Problems.Design_ParkingLot_System.Vehicle.VehicleType;

public interface ParkingFeeStrategy {
    double calculateFee(VehicleType vehicleType, int duration, DurationType durationType);    
}
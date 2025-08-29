package LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ConcreteFeeStrategy;

import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.DurationType;
import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ParkingFeeStrategy;
import LLD_Interview_Problems.Design_ParkingLot_System.Vehicle.VehicleType;

public class BasicHourlyFeeStrategy implements ParkingFeeStrategy{

    @Override
    public double calculateFee(VehicleType vehicleType, int duration, DurationType durationType) {
        switch (vehicleType) {
            case CAR:
                return durationType == DurationType.HOURS ? duration * 10: duration * 10 * 24;
            case BIKE:
                return durationType == DurationType.HOURS ? duration * 5: duration * 5 * 24;
            case TRUCK:
                return durationType == DurationType.HOURS ? duration * 15: duration * 15 * 24;
            default:
                return durationType == DurationType.HOURS ? duration * 10: duration * 10 * 24;
        }
    }
}
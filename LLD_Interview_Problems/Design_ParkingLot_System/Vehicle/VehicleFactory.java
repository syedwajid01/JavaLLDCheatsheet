package LLD_Interview_Problems.Design_ParkingLot_System.Vehicle;

import LLD_Interview_Problems.Design_ParkingLot_System.Strategies.FeeStrategy.ParkingFeeStrategy;

public class VehicleFactory {
    public static Vehicle createVehicle(VehicleType vehicleType, String licenseNumber, ParkingFeeStrategy parkingFeeStrategy) {
        switch (vehicleType) {
            case CAR:
                return new Car(licenseNumber, parkingFeeStrategy);
            case BIKE:
                return new Bike(licenseNumber, parkingFeeStrategy);
            case TRUCK:
                return new Truck(licenseNumber, parkingFeeStrategy);
            default:
                throw new IllegalArgumentException("Invalid vechile type");
        }
    }
}

package LLD_Interview_Problems.Design_ParkingLot_System.Entities;

import java.util.List;

import LLD_Interview_Problems.Design_ParkingLot_System.Vehicle.Vehicle;
import LLD_Interview_Problems.Design_ParkingLot_System.Vehicle.VehicleType;

public class ParkingLot {
    private List<ParkingSpot> parkingSpots;

    public ParkingLot(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public ParkingSpot findAvailableSpot(VehicleType vehicleType) {
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (!parkingSpot.isOccupied() && parkingSpot.getSpotType() == vehicleType) {
                return parkingSpot;
            }
        }
        return null;
    }

    public ParkingSpot parkVechile(Vehicle vehicle) {
        ParkingSpot parkingSpot = findAvailableSpot(vehicle.getVehicleType());
        if (parkingSpot != null) {
            parkingSpot.parkVechile(vehicle);
            System.out.println("Vehicle is parked successfully at " + parkingSpot.getSpotNumber());
            return parkingSpot;
        }
        return null;
    }

    public void vacateSpot(ParkingSpot parkingSpot, Vehicle vehicle) {
        if (parkingSpot != null && parkingSpot.isOccupied() && parkingSpot.getVehicle().equals(vehicle)) {
            parkingSpot.vacateVehicle();
            System.out.println(vehicle.getVehicleType() + " Vacated the spot " + parkingSpot.getSpotNumber());
        } else {
            System.out.println("Either Parking spot is already vacant or vehicle does not match");
        }
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
}

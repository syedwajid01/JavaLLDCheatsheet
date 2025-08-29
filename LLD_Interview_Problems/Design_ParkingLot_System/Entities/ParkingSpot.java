package LLD_Interview_Problems.Design_ParkingLot_System.Entities;

import LLD_Interview_Problems.Design_ParkingLot_System.Vehicle.Vehicle;
import LLD_Interview_Problems.Design_ParkingLot_System.Vehicle.VehicleType;

public class ParkingSpot {
    private int spotNumber;
    private VehicleType spotType;
    private Vehicle vehicle;
    private boolean isOccupied;

    public ParkingSpot(int spotNumber, VehicleType spotType) {
        this.spotNumber = spotNumber;
        this.spotType = spotType;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getSpotNumber() {
        return spotNumber;
    }

    public VehicleType getSpotType() {
        return spotType;
    }

    public boolean canParkVehicle(Vehicle vehicle) {
        return spotType == vehicle.getVehicleType();
    }

    public void parkVechile(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isOccupied = true;
    }

    public void vacateVehicle() {
        this.vehicle = null;
        this.isOccupied = false;
    }
}

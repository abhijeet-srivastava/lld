package com.twisha.parkinglot.service;

public class ParkingSpotFactory {

    public static ParkingSpot createParkingSpot(Integer spotId, VehicleType type) {
        switch (type) {
            case BIKE :
                return new BikeSpot(spotId);
            case TRUCK:
                return new TruckSpot(spotId);
            case CAR:
                return new CarSpot(spotId);
            default:
                throw new IllegalArgumentException();
        }
    }
}

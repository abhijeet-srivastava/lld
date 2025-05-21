package com.twisha.parkinglot.service;

public class BikeSpot implements ParkingSpot {
    Integer id;
    public BikeSpot(Integer spotId) {
        this.id = spotId;
    }

    @Override
    public Integer getSpotId() {
        return this.id;
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.BIKE;
    }
}

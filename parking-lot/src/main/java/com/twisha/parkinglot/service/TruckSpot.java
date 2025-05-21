package com.twisha.parkinglot.service;

public class TruckSpot implements ParkingSpot {
    private Integer id;
    public TruckSpot(Integer spotId) {
        this.id = spotId;
    }

    @Override
    public Integer getSpotId() {
        return this.id;
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.TRUCK;
    }
}

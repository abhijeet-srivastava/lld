package com.twisha.parkinglot.service;

public class CarSpot implements ParkingSpot {

    private Integer id;
    public CarSpot(Integer spotId) {
        this.id = spotId;
    }

    @Override
    public Integer getSpotId() {
        return this.id;
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.CAR;
    }
}

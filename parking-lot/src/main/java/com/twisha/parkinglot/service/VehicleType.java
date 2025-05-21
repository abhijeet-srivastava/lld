package com.twisha.parkinglot.service;

public enum VehicleType {
    TRUCK(10),
    CAR(5),
    BIKE(1);

    private Integer size;

    VehicleType(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }
}

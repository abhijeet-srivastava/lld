package com.twisha.parkinglot.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLot {


    private final Map<Integer, ParkingSpot> parkingSpots;
    private final AtomicInteger idGenerator;


    private final ParkingStrategy  strategy;

    private ParkingLot(ParkingStrategy strategy) {
        this.parkingSpots = new ConcurrentHashMap<>();
        this.idGenerator = new AtomicInteger(1);
        this.strategy = strategy;
    }

    public static void initialize(ParkingStrategy strategy) {
        ParkingLotInitiator.assignParkingStrategy(strategy);
    }

    public ParkingLot getParkingLot() {
        return ParkingLotInitiator.INSTANCE;
    }

    private static class ParkingLotInitiator {
        private static  ParkingLot INSTANCE;

        static void assignParkingStrategy(ParkingStrategy strategy) {
            if(INSTANCE == null) {
                INSTANCE = new ParkingLot(strategy);
            } else {
                throw new IllegalStateException("Invalid initialisation stage");
            }
        }
    }
}

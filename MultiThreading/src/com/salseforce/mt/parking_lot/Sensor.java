package com.salseforce.mt.parking_lot;

import java.util.concurrent.TimeUnit;

public class Sensor implements Runnable {
    private ParkingStats parkingStats;

    public Sensor(ParkingStats parkingStats) {
        this.parkingStats = parkingStats;
    }

    @Override
    public void run() {
        for(int i = 0; i < 15; i++) {
            parkingStats.carComesIn();
            parkingStats.carComesIn();
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            parkingStats.motoComesIn();
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            parkingStats.motoGoesOut();
            parkingStats.carGoesOut();
            parkingStats.carGoesOut();
        }
    }
}

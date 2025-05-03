package com.salseforce.mt.parking_lot;

public class ParkingStats {
    private int numOfMoto;
    private int numOfCars;

    private ParkingCash parkingCash;

    private final Object controlCars, controlMotorcycles;

    public ParkingStats(ParkingCash parkingCash, Object controlCars, Object controlMotorcycles) {
        this.controlCars = controlCars;
        this.controlMotorcycles = controlMotorcycles;
        this.numOfMoto = 0;
        this.numOfCars = 0;
        this.parkingCash = parkingCash;
    }
    public void  carComesIn(){
        synchronized (controlCars) {
            this.numOfCars += 1;
        }
    }
    public void  motoComesIn(){
        synchronized (controlMotorcycles) {
            this.numOfMoto += 1;
        }
    }
    public void carGoesOut() {
        synchronized (controlCars) {
            this.numOfCars -= 1;
        }
        this.parkingCash.vehiclePay();
    }
    public  void motoGoesOut() {
        synchronized (controlMotorcycles) {
            this.numOfMoto -= 1;
        }
        this.parkingCash.vehiclePay();
    }

    public int getNumOfMoto() {
        return numOfMoto;
    }

    public int getNumOfCars() {
        return numOfCars;
    }
}

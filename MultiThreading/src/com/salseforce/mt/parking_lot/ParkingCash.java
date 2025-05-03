package com.salseforce.mt.parking_lot;

public class ParkingCash {
    private static final Integer cost = 2;

    private long cash;

    public ParkingCash() {
        this.cash = 0;
    }

    public synchronized void vehiclePay() {
        cash+=cost;
    }
    public void close() {
        System.out.println("Closing accounts");
        long totalAmount;
        synchronized (this) {
            totalAmount = this.cash;
            this.cash = 0;
        }
        System.out.println("Total Amt:" + totalAmount);
    }

}

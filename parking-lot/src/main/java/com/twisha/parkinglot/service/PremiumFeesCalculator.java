package com.twisha.parkinglot.service;

public class PremiumFeesCalculator implements ParkingFeeCalculator {

    private final double surCharge;

    private final ParkingFeeCalculator wrapped;

    public PremiumFeesCalculator(ParkingFeeCalculator wrapped, double surCharge) {
        this.wrapped = wrapped;
        this.surCharge = surCharge;
    }

    @Override
    public double calculateFees(Ticket ticket) {
        double basePrice = this.wrapped.calculateFees(ticket);
        return basePrice + basePrice*surCharge/100d;
    }
}

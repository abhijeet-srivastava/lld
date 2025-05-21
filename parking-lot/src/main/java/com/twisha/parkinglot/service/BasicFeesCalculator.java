package com.twisha.parkinglot.service;

public class BasicFeesCalculator implements  ParkingFeeCalculator {

    private final double rate;

    public BasicFeesCalculator(double rate) {
        this.rate = rate;
    }

    @Override
    public double calculateFees(Ticket ticket) {
        return ticket.getDuration()*this.rate;
    }
}

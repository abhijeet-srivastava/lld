package com.twisha.parkinglot.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PremiumFeesCalculatorTest {

    @Test
    public void testPremiumParkingFees() {
        Ticket ticket = Mockito.mock(Ticket.class);
        BasicFeesCalculator bpc = Mockito.mock(BasicFeesCalculator.class);
        Mockito.when(bpc.calculateFees(Mockito.any(Ticket.class))).thenReturn(10.0d);
        PremiumFeesCalculator premiumFeesCalculator = new PremiumFeesCalculator(bpc,1);

        double premiumFees = premiumFeesCalculator.calculateFees(ticket);
        assertEquals(10.1d, premiumFees);
    }

}
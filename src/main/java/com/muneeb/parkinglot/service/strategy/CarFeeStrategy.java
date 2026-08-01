package com.muneeb.parkinglot.service.strategy;

import org.springframework.stereotype.Component;

@Component
public class CarFeeStrategy implements FeeCalculationStrategy{


    @Override
    public double CalculateFee(long hours) {
        return hours *20;
    }
}

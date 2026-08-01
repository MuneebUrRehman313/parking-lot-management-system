package com.muneeb.parkinglot.service.strategy;

import org.springframework.stereotype.Component;

@Component
public class TruckFeeStrategy  implements FeeCalculationStrategy{

    @Override
    public double CalculateFee(long hours) {
        return hours * 30;
    }
}

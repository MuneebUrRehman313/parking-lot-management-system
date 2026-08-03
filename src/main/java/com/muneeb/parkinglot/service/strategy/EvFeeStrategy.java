package com.muneeb.parkinglot.service.strategy;

import com.muneeb.parkinglot.enums.VehicleType;

public class EvFeeStrategy implements FeeCalculationStrategy{

    @Override
    public double CalculateFee(long hours) {
        return hours * 40;
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.EV;
    }
}

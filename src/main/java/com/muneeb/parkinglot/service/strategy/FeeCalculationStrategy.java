package com.muneeb.parkinglot.service.strategy;

import com.muneeb.parkinglot.enums.VehicleType;

public interface FeeCalculationStrategy {


    double CalculateFee(long hours);


    VehicleType getVehicleType();
}

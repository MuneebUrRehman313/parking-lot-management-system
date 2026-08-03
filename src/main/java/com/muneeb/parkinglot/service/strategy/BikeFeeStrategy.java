package com.muneeb.parkinglot.service.strategy;


import com.muneeb.parkinglot.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class BikeFeeStrategy implements FeeCalculationStrategy{


    @Override
    public double CalculateFee(long hours) {
        return hours *10;
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.BIKE;
    }
}

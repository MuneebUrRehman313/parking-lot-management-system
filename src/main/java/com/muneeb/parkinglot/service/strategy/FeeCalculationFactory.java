package com.muneeb.parkinglot.service.strategy;

import com.muneeb.parkinglot.enums.VehicleType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FeeCalculationFactory {

    private final CarFeeStrategy carFeeStrategy;
    private final BikeFeeStrategy bikeFeeStrategy;
    private final TruckFeeStrategy truckFeeStrategy;

    public FeeCalculationStrategy getStrategy(VehicleType vehicleType){

        switch (vehicleType){
            case CAR :
                return carFeeStrategy;
            case BIKE:
                return bikeFeeStrategy;
            case TRUCK:
                return truckFeeStrategy;
            default:
                throw new RuntimeException("invalid vehicle type ");
        }
    }

}

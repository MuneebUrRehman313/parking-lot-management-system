package com.muneeb.parkinglot.service.strategy;

import com.muneeb.parkinglot.enums.VehicleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class FeeCalculationFactory {

    private final Map<VehicleType, FeeCalculationStrategy> strategyMap;

    public FeeCalculationFactory(List<FeeCalculationStrategy> strategies) {

        strategyMap = new EnumMap<>(VehicleType.class);

        for (FeeCalculationStrategy strategy : strategies) {
            strategyMap.put(strategy.getVehicleType(), strategy);
        }
    }

    public FeeCalculationStrategy getStrategy(VehicleType vehicleType) {

        FeeCalculationStrategy strategy = strategyMap.get(vehicleType);

        if (strategy == null) {
            throw new RuntimeException("No strategy found for " + vehicleType);
        }

        return strategy;
    }
}
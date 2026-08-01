package com.muneeb.parkinglot.service.impl;

import com.muneeb.parkinglot.entity.Ticket;
import com.muneeb.parkinglot.enums.VehicleType;
import com.muneeb.parkinglot.service.FeeCalculationService;
import com.muneeb.parkinglot.service.strategy.FeeCalculationFactory;
import com.muneeb.parkinglot.service.strategy.FeeCalculationStrategy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class FeeCalculationServiceImpl implements FeeCalculationService {

    private final FeeCalculationFactory feeCalculationFactory;
  public Double calculateFee(Ticket ticket){

      long hours = Duration.between(
              ticket.getEntryTime(),
              ticket.getExitTime()
      ).toHours();

      if(hours ==0 ){
          hours = 1 ;
      }


      FeeCalculationStrategy strategy = feeCalculationFactory.getStrategy(ticket.getVehicle().getVehicleType());

      return strategy.CalculateFee(hours);


  }



}

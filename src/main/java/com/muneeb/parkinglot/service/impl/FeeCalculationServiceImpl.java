package com.muneeb.parkinglot.service.impl;

import com.muneeb.parkinglot.entity.Ticket;
import com.muneeb.parkinglot.enums.VehicleType;
import com.muneeb.parkinglot.service.FeeCalculationService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class FeeCalculationServiceImpl implements FeeCalculationService {

  public Double calculateFee(Ticket ticket){

      long hours = Duration.between(
              ticket.getEntryTime(),
              ticket.getExitTime()
      ).toHours();

      if(hours ==0 ){
          hours = 1 ;
      }

      double hourlyRate = getHourlyType(ticket.getVehicle().getVehicleType());

      return  hourlyRate * hours;


  }

  public double getHourlyType(VehicleType vehicleType){

      return switch (vehicleType){

          case CAR->20;

          case BIKE -> 10;

          case TRUCK -> 30;
      };
  }


}

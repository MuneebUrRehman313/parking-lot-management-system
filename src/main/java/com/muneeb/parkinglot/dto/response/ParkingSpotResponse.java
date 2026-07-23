package com.muneeb.parkinglot.dto.response;

import com.muneeb.parkinglot.enums.ParkingSpotStatus;
import com.muneeb.parkinglot.enums.ParkingSpotType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParkingSpotResponse  {

    private Long id ;

    private String spotNumber;

    private ParkingSpotType spotType;

    private ParkingSpotStatus status;

    private Long floorId;

    private Integer floorNumber;

}

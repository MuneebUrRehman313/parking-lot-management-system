package com.muneeb.parkinglot.dto.request;

import com.muneeb.parkinglot.enums.ParkingSpotType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateParkingSpotRequest {

    @NotBlank(message = "Spot number is required")
    private String spotNumber;

    @NotNull(message = "Spot type is required")
    private ParkingSpotType spotType;

    @NotNull(message = "Floor id is required")
    private Integer floorNumber;

}
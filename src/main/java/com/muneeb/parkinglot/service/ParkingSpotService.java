package com.muneeb.parkinglot.service;

import com.muneeb.parkinglot.dto.request.CreateParkingSpotRequest;
import com.muneeb.parkinglot.dto.response.ParkingSpotResponse;

import java.util.List;

public interface ParkingSpotService {


    ParkingSpotResponse createSpot(CreateParkingSpotRequest request);
    List<ParkingSpotResponse> getAllSpot();
    ParkingSpotResponse getSpotById(Long id);
    ParkingSpotResponse updateSpot(Long id ,CreateParkingSpotRequest request);

    void deleteSpot(Long id);
}

package com.muneeb.parkinglot.service;

import com.muneeb.parkinglot.dto.request.CreateParkingFloorRequest;
import com.muneeb.parkinglot.dto.response.ParkingFloorResponse;
import com.muneeb.parkinglot.entity.ParkingFloor;

import java.util.List;

public interface ParkingFloorService {

    ParkingFloorResponse createFloor(CreateParkingFloorRequest request);

    List<ParkingFloorResponse> getAllFloors();

    ParkingFloorResponse getFloorById(Long id );

    ParkingFloorResponse updateFloor(Long id ,CreateParkingFloorRequest request);

    void deleteFloor(Long id);
}

package com.muneeb.parkinglot.service;

import com.muneeb.parkinglot.dto.request.CreateVehicleRequest;
import com.muneeb.parkinglot.dto.response.VehicleResponse;

import java.util.List;

public interface VehicleService {

    VehicleResponse createVehicle(CreateVehicleRequest request);

    VehicleResponse getVehicleById(Long id);

    List<VehicleResponse> getAllVehicles();

    VehicleResponse updateVehicle(Long id, CreateVehicleRequest request);

    void deleteVehicle(Long id);

}
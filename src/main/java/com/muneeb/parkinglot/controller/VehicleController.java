package com.muneeb.parkinglot.controller;

import com.muneeb.parkinglot.dto.request.CreateVehicleRequest;
import com.muneeb.parkinglot.dto.response.VehicleResponse;
import com.muneeb.parkinglot.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public VehicleResponse createVehicle(
            @Valid @RequestBody CreateVehicleRequest request) {

        return vehicleService.createVehicle(request);
    }

    @GetMapping("/{id}")
    public VehicleResponse getVehicleById(@PathVariable Long id) {

        return vehicleService.getVehicleById(id);
    }

    @GetMapping
    public List<VehicleResponse> getAllVehicles() {

        return vehicleService.getAllVehicles();
    }

    @PutMapping("/{id}")
    public VehicleResponse updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody CreateVehicleRequest request) {

        return vehicleService.updateVehicle(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable Long id) {

        vehicleService.deleteVehicle(id);
        return "Vehicle deleted successfully.";
    }
}
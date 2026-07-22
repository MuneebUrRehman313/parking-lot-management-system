package com.muneeb.parkinglot.controller;

import com.muneeb.parkinglot.dto.request.CreateParkingFloorRequest;
import com.muneeb.parkinglot.dto.response.ParkingFloorResponse;
import com.muneeb.parkinglot.service.ParkingFloorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floors")
@RequiredArgsConstructor
public class ParkingFloorController {

    private final ParkingFloorService parkingFloorService;

    @PostMapping
    public ParkingFloorResponse createFloor(@Valid @RequestBody CreateParkingFloorRequest request) {
        return parkingFloorService.createFloor(request);
    }

    @GetMapping
    public List<ParkingFloorResponse> getAllFloors() {
        return parkingFloorService.getAllFloors();
    }

    @GetMapping("/{id}")
    public ParkingFloorResponse getFloorById(@PathVariable Long id) {
        return parkingFloorService.getFloorById(id);
    }

    @PutMapping("/{id}")
    public ParkingFloorResponse updateFloor(
            @PathVariable Long id,
            @Valid @RequestBody CreateParkingFloorRequest request) {

        return parkingFloorService.updateFloor(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteFloor(@PathVariable Long id) {
        parkingFloorService.deleteFloor(id);
    }
}
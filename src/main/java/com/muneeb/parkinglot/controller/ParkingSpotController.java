package com.muneeb.parkinglot.controller;

import com.muneeb.parkinglot.dto.request.CreateParkingSpotRequest;
import com.muneeb.parkinglot.dto.response.ParkingSpotResponse;
import com.muneeb.parkinglot.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spots")
@RequiredArgsConstructor
public class ParkingSpotController {


    private  final ParkingSpotService parkingSpotService;

    @PostMapping
    public ParkingSpotResponse createSpot(@RequestBody CreateParkingSpotRequest request){

        return parkingSpotService.createSpot(request);
    }

    @GetMapping
    public List<ParkingSpotResponse> getAllSpots(){
        return parkingSpotService.getAllSpot();
    }

    @GetMapping("/{id}")
    public  ParkingSpotResponse getSpotById(@PathVariable Long id){
        return parkingSpotService.getSpotById(id);
    }

    @PutMapping("/{id}")
    public ParkingSpotResponse updateSpot(
            @PathVariable Long id,
            @RequestBody CreateParkingSpotRequest request){
        return parkingSpotService.updateSpot(id, request);
    }
    @DeleteMapping("/{id}")
    void deleteSpot(@PathVariable Long id){
        parkingSpotService.deleteSpot(id);
    }
}

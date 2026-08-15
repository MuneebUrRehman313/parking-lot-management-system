package com.muneeb.parkinglot.controller;

import com.muneeb.parkinglot.dto.request.CreateParkingSpotRequest;
import com.muneeb.parkinglot.dto.response.ParkingSpotResponse;
import com.muneeb.parkinglot.service.ParkingSpotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spots")
@RequiredArgsConstructor
@Tag(
        name = "Parking Spot APIs",
        description = "APIs for managing parking spots"
)
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;


    @Operation(
            summary = "Create a parking spot",
            description = "Creates and stores a new parking spot"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parking spot created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parking spot data"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Parking spot already exists"
            )
    })
    @PostMapping
    public ParkingSpotResponse createSpot(
            @RequestBody CreateParkingSpotRequest request) {

        return parkingSpotService.createSpot(request);
    }


    @Operation(
            summary = "Get all parking spots",
            description = "Retrieves all parking spots in the parking system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Parking spots retrieved successfully"
    )
    @GetMapping
    public List<ParkingSpotResponse> getAllSpots() {

        return parkingSpotService.getAllSpot();
    }


    @Operation(
            summary = "Get parking spot by ID",
            description = "Retrieves a parking spot using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parking spot found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Parking spot not found"
            )
    })
    @GetMapping("/{id}")
    public ParkingSpotResponse getSpotById(
            @PathVariable Long id) {

        return parkingSpotService.getSpotById(id);
    }


    @Operation(
            summary = "Update parking spot",
            description = "Updates the details of an existing parking spot"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parking spot updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parking spot data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Parking spot not found"
            )
    })
    @PutMapping("/{id}")
    public ParkingSpotResponse updateSpot(
            @PathVariable Long id,
            @RequestBody CreateParkingSpotRequest request) {

        return parkingSpotService.updateSpot(id, request);
    }


    @Operation(
            summary = "Delete parking spot",
            description = "Deletes a parking spot using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parking spot deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Parking spot not found"
            )
    })
    @DeleteMapping("/{id}")
    public void deleteSpot(@PathVariable Long id) {

        parkingSpotService.deleteSpot(id);
    }
}
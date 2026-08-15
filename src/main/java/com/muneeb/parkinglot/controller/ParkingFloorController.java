package com.muneeb.parkinglot.controller;

import com.muneeb.parkinglot.dto.request.CreateParkingFloorRequest;
import com.muneeb.parkinglot.dto.response.ParkingFloorResponse;
import com.muneeb.parkinglot.service.ParkingFloorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/floors")
@RequiredArgsConstructor
@Tag(
        name = "Parking Floor APIs",
        description = "APIs for managing parking floors"
)
public class ParkingFloorController {

    private final ParkingFloorService parkingFloorService;

    @Operation(
            summary = "Create a parking floor",
            description = "Creates and stores a new parking floor"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parking floor created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parking floor data"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Parking floor already exists"
            )
    })
    @PostMapping
    public ParkingFloorResponse createFloor(
            @Valid @RequestBody CreateParkingFloorRequest request) {

        return parkingFloorService.createFloor(request);
    }


    @Operation(
            summary = "Get all parking floors",
            description = "Retrieves all parking floors"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Parking floors retrieved successfully"
    )
    @GetMapping
    public List<ParkingFloorResponse> getAllFloors() {

        return parkingFloorService.getAllFloors();
    }


    @Operation(
            summary = "Get parking floor by ID",
            description = "Retrieves a parking floor using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parking floor found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Parking floor not found"
            )
    })
    @GetMapping("/{id}")
    public ParkingFloorResponse getFloorById(
            @PathVariable Long id) {

        return parkingFloorService.getFloorById(id);
    }


    @Operation(
            summary = "Update parking floor",
            description = "Updates the details of an existing parking floor"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parking floor updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parking floor data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Parking floor not found"
            )
    })
    @PutMapping("/{id}")
    public ParkingFloorResponse updateFloor(
            @PathVariable Long id,
            @Valid @RequestBody CreateParkingFloorRequest request) {

        return parkingFloorService.updateFloor(id, request);
    }


    @Operation(
            summary = "Delete parking floor",
            description = "Deletes a parking floor using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Parking floor deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Parking floor not found"
            )
    })
    @DeleteMapping("/{id}")
    public void deleteFloor(
            @PathVariable Long id) {

        parkingFloorService.deleteFloor(id);
    }
}
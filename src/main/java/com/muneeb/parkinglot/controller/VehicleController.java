package com.muneeb.parkinglot.controller;

import com.muneeb.parkinglot.dto.request.CreateVehicleRequest;
import com.muneeb.parkinglot.dto.response.VehicleResponse;
import com.muneeb.parkinglot.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;


@Tag(
        name = "Vehicle APIs",
        description = "APIs for managing vehicles"
)
@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(
            summary = "create a new vehicle",
            description = "creates and stores a new vehicle in the parking system"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "vehicle created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "invalid vehicle data"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "vehicle already exists"
            )
    })
    @PostMapping
    public VehicleResponse createVehicle(
            @Valid @RequestBody CreateVehicleRequest request) {

        return vehicleService.createVehicle(request);
    }

    @Operation(

            summary = "get vehicle by id ",
            description = "Retrieves a vehicle using it unique Id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "vehicle found successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "vehicle not found"
            )
    })
    @GetMapping("/{id}")
    public VehicleResponse getVehicleById(@PathVariable Long id) {

        return vehicleService.getVehicleById(id);
    }

    @Operation(
            summary = "get all vehicles",
            description = "Retrieves all vehicles registered in the parking system"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrives all vehicle sucessfully"
            ),
    })
    @GetMapping
    public List<VehicleResponse> getAllVehicles() {

        return vehicleService.getAllVehicles();
    }

    @Operation(
            summary = "Update vehicle",
            description = "Updates the details of an existing vehicle"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "vehicle data updated successfully "
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "invalid data "
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "alredy exists thts data"
            )
    })
    @PutMapping("/{id}")
    public VehicleResponse updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody CreateVehicleRequest request) {

        return vehicleService.updateVehicle(id, request);
    }

    @Operation(
            summary = "delete vehicle",
            description = "Deletes a vehicle from parking system "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "vehicle deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "vehicle not found"
            )
    })
    @DeleteMapping("/{id}")
    public String deleteVehicle(@PathVariable Long id) {

        vehicleService.deleteVehicle(id);
        return "Vehicle deleted successfully.";
    }
}
package com.muneeb.parkinglot.dto.request;

import com.muneeb.parkinglot.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateVehicleRequest {

    @NotBlank(message = "vehicle number is required")
    @Size(max = 20,message = "vehicle number cannot exceed 20 character")
    private  String vehicleNumber;

    @NotBlank(message = "owner name is required")
    @Size(max = 100,message = "owner name cannot exceed 100 characters")
    private String ownerName;

    @NotNull(message = "vehicle type is required")
    private VehicleType vehicleType;
}

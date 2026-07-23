package com.muneeb.parkinglot.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTicketRequest {

    @NotBlank(message = "vehicle number is required")
    private String vehicleNumber;
}

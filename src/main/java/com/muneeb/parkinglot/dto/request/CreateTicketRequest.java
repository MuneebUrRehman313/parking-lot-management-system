package com.muneeb.parkinglot.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTicketRequest {

    @NotNull(message = "vehicle id is required")

    private Long vehicleId;
}

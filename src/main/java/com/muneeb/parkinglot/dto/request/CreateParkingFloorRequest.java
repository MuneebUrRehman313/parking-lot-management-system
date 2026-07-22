package com.muneeb.parkinglot.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.aspectj.bridge.IMessage;
import org.aspectj.bridge.Message;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateParkingFloorRequest {

    @NotNull(message = "floor number is required")
    @Min(value = 1,message = "floor number must be greater than 0 ")
    private Integer floorNumber;

    @NotBlank(message = "floor name is required")
    private String name;

    @NotNull(message = "capacity is required")
    @Min(value = 1,message = "capacity must be greater than 0 ")
    private Integer capacity;


}

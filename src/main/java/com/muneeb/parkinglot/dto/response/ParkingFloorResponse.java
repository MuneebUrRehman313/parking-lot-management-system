package com.muneeb.parkinglot.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingFloorResponse {
    private Long id ;

    private  Integer floorNumber;

    private  String name ;

    private Integer capacity;
}

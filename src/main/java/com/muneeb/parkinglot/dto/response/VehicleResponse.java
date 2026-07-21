package com.muneeb.parkinglot.dto.response;

import com.muneeb.parkinglot.enums.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponse {

     private  Long id ;

     private  String vehicleNumber;

     private String ownerName;

     private VehicleType vehicleType;
}

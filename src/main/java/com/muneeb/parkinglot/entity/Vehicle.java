package com.muneeb.parkinglot.entity;

import com.muneeb.parkinglot.enums.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "vehicle_number",nullable = false,unique = true,length = 20)
    private String vehicleNumber;

    @Column(name = "owner_name",nullable = false,length = 100)
    private String ownerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type",nullable = false)
    private VehicleType vehicleType;
}

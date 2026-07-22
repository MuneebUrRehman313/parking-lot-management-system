package com.muneeb.parkinglot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_floors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingFloor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "floor_number", nullable = false, unique = true)
    private Integer floorNumber;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "parkingFloor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ParkingSpot> parkingSpots = new ArrayList<>();

}
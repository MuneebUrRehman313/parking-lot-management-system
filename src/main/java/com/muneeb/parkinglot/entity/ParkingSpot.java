package com.muneeb.parkinglot.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "parking_spots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spot_number", nullable = false, unique = true)
    private String spotNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", nullable = false)
    private ParkingFloor parkingFloor;

}
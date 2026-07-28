package com.muneeb.parkinglot.repository;

import com.muneeb.parkinglot.entity.ParkingFloor;
import com.muneeb.parkinglot.entity.ParkingSpot;
import com.muneeb.parkinglot.enums.ParkingSpotStatus;
import com.muneeb.parkinglot.enums.ParkingSpotType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot,Long> {


//    Ye kya karega?

//    Database me check karega ki ye spot already exist karta hai ya nahi.
    Optional<ParkingSpot> findBySpotNumber(String spotNumber);

    Optional<ParkingSpot> findFirstBySpotTypeAndStatus(
            ParkingSpotType spotType,
            ParkingSpotStatus status
    );

    List<ParkingSpot> findBySpotTypeAndStatus(
            ParkingSpotType spotType,
            ParkingSpotStatus status
    );
}

package com.muneeb.parkinglot.repository;

import com.muneeb.parkinglot.entity.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloor,Long> {
    Optional<ParkingFloor> findByFloorNumber(Integer floorNumber);

    boolean existsByFloorNumber(Integer floorNumber);
}

package com.muneeb.parkinglot.service.impl;

import com.muneeb.parkinglot.dto.request.CreateParkingSpotRequest;
import com.muneeb.parkinglot.dto.response.ParkingSpotResponse;
import com.muneeb.parkinglot.entity.ParkingFloor;
import com.muneeb.parkinglot.entity.ParkingSpot;
import com.muneeb.parkinglot.enums.ParkingSpotStatus;
import com.muneeb.parkinglot.exception.DuplicateResourceException;
import com.muneeb.parkinglot.exception.ResourceNotFoundException;
import com.muneeb.parkinglot.repository.ParkingFloorRepository;
import com.muneeb.parkinglot.repository.ParkingSpotRepository;
import com.muneeb.parkinglot.service.ParkingSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ParkingFloorRepository parkingFloorRepository;

    @Override
    public ParkingSpotResponse createSpot(CreateParkingSpotRequest request) {

        parkingSpotRepository.findBySpotNumber(request.getSpotNumber())
                .ifPresent(spot -> {
                    throw new DuplicateResourceException("Spot number already exists");
                });

        ParkingFloor floor = parkingFloorRepository
                .findByFloorNumber(request.getFloorNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Parking floor not found"));

        ParkingSpot parkingSpot = ParkingSpot.builder()
                .spotNumber(request.getSpotNumber())
                .spotType(request.getSpotType())
                .status(ParkingSpotStatus.AVAILABLE)
                .parkingFloor(floor)
                .build();

        ParkingSpot savedSpot = parkingSpotRepository.save(parkingSpot);

        return mapToResponse(savedSpot);
    }

    @Override
    public List<ParkingSpotResponse> getAllSpot() {

        return parkingSpotRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ParkingSpotResponse getSpotById(Long id) {

        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking spot not found"));

        return mapToResponse(spot);
    }

    @Override
    public ParkingSpotResponse updateSpot(Long id, CreateParkingSpotRequest request) {

        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking spot not found"));

        parkingSpotRepository.findBySpotNumber(request.getSpotNumber())
                .ifPresent(existingSpot -> {
                    if (!existingSpot.getId().equals(id)) {
                        throw new DuplicateResourceException("Spot number already exists");
                    }
                });

        ParkingFloor floor = parkingFloorRepository
                .findByFloorNumber(request.getFloorNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Parking floor not found"));
        spot.setSpotNumber(request.getSpotNumber());
        spot.setSpotType(request.getSpotType());
        spot.setParkingFloor(floor);

        ParkingSpot updatedSpot = parkingSpotRepository.save(spot);

        return mapToResponse(updatedSpot);
    }

    @Override
    public void deleteSpot(Long id) {

        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking spot not found"));

        parkingSpotRepository.delete(spot);
    }

    private ParkingSpotResponse mapToResponse(ParkingSpot spot) {

        return ParkingSpotResponse.builder()
                .id(spot.getId())
                .spotNumber(spot.getSpotNumber())
                .spotType(spot.getSpotType())
                .status(spot.getStatus())
                .floorId(spot.getParkingFloor().getId())
                .floorNumber(spot.getParkingFloor().getFloorNumber())
                .build();
    }
}
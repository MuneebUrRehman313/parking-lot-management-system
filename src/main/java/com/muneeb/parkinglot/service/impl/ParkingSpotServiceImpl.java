package com.muneeb.parkinglot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ParkingSpotServiceImpl.class);

    @Override
    public ParkingSpotResponse createSpot(CreateParkingSpotRequest request) {


        logger.debug("Creating spot with spotNumber {}",request.getSpotNumber());
        parkingSpotRepository.findBySpotNumber(request.getSpotNumber())
                .ifPresent(spot -> {
                    logger.warn("spot already exists with this spotNumber {}",request.getSpotNumber());
                    throw new DuplicateResourceException("Spot number already exists");
                });

        ParkingFloor floor = parkingFloorRepository
                .findByFloorNumber(request.getFloorNumber())
                .orElseThrow(() -> {
                    logger.warn("floor not found {}",request.getFloorNumber());
                    return new ResourceNotFoundException("Parking floor not found");
                });

        ParkingSpot parkingSpot = ParkingSpot.builder()
                .spotNumber(request.getSpotNumber())
                .spotType(request.getSpotType())
                .status(ParkingSpotStatus.AVAILABLE)
                .parkingFloor(floor)
                .build();

        ParkingSpot savedSpot = parkingSpotRepository.save(parkingSpot);
        logger.info("Parking Spot created succesFully with spot id  : {} and spotNumber {}",
                savedSpot.getId(),
                savedSpot.getSpotNumber());

        return mapToResponse(savedSpot);
    }

    @Override
    public List<ParkingSpotResponse> getAllSpot() {

        logger.debug("fetching all spots");
        return parkingSpotRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ParkingSpotResponse getSpotById(Long id) {

        logger.debug("fetching spot by id {} ",id);
        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> {
                      logger.warn("parking spot not found wiht id {}",id);
                       return new ResourceNotFoundException("Parking spot not found");
    });


        return mapToResponse(spot);
    }

    @Override
    public ParkingSpotResponse updateSpot(Long id, CreateParkingSpotRequest request) {

        logger.debug("fetching id for update {}",id);
        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() ->{

                    logger.warn("Parking spot not found with id {}",id);
                    return  new ResourceNotFoundException("Parking spot not found");
    });

        parkingSpotRepository.findBySpotNumber(request.getSpotNumber())
                .ifPresent(existingSpot -> {
                    if (!existingSpot.getId().equals(id)) {
                        logger.warn("spot already exists with spot number{}",request.getSpotNumber());
                        throw new DuplicateResourceException("Spot number already exists");
                    }
                });

        ParkingFloor floor = parkingFloorRepository
                .findByFloorNumber(request.getFloorNumber())
                .orElseThrow(() -> {
                        logger.warn("parking floor not found with floornumber {}",request.getFloorNumber());
                         return new ResourceNotFoundException("Parking floor not found");
    });
        spot.setSpotNumber(request.getSpotNumber());
        spot.setSpotType(request.getSpotType());
        spot.setParkingFloor(floor);

        ParkingSpot updatedSpot = parkingSpotRepository.save(spot);
        logger.info("update succesfully wiht id {} and spot number {}",
                updatedSpot.getId(),
                updatedSpot.getSpotNumber());

        return mapToResponse(updatedSpot);
    }

    @Override
    public void deleteSpot(Long id) {

        logger.debug("fetching spot by id for delete {}",id);
        ParkingSpot spot = parkingSpotRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("parking spot not found with id for delete {}",id);
                       return  new ResourceNotFoundException("Parking spot not found");
    });

        parkingSpotRepository.delete(spot);
        logger.info("spot deleted succesfully with id {}",id);
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
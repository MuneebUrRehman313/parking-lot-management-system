package com.muneeb.parkinglot.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.muneeb.parkinglot.dto.request.CreateParkingFloorRequest;
import com.muneeb.parkinglot.dto.response.ParkingFloorResponse;
import com.muneeb.parkinglot.entity.ParkingFloor;
import com.muneeb.parkinglot.exception.DuplicateResourceException;
import com.muneeb.parkinglot.exception.ResourceNotFoundException;
import com.muneeb.parkinglot.repository.ParkingFloorRepository;
import com.muneeb.parkinglot.service.ParkingFloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


//         Logic Samjho
//         createFloor()
//           Admin
//
//             ↓
//
//         Create Floor
//
//             ↓
//
//          Check Duplicate
//
//             ↓
//
//           Save
//
//            ↓
//
//         Return Response
@Service
@RequiredArgsConstructor
public class ParkingFloorServiceImpl implements ParkingFloorService {

    private final ParkingFloorRepository parkingFloorRepository;
    private static final Logger logger = LoggerFactory.getLogger(ParkingFloorServiceImpl.class);

    //create floor
    @Override
    public ParkingFloorResponse createFloor(CreateParkingFloorRequest request) {


        logger.debug("Creating floor with floor number: {}", request.getFloorNumber());
        //check duplicate
        if (parkingFloorRepository.existsByFloorNumber(request.getFloorNumber())) {
            logger.warn("Floor already exists with this floorNumber {}",request.getFloorNumber());
            throw new DuplicateResourceException("floor already exists");
        }

        ParkingFloor parkingFloor = ParkingFloor.builder()
                .floorNumber(request.getFloorNumber())
                .name(request.getName())
                .capacity(request.getCapacity())
                .build();

        ParkingFloor savedFloor = parkingFloorRepository.save(parkingFloor);
        logger.info("Floor created successfully with floor number : {} and with capacity {}",
                savedFloor.getFloorNumber(),
                savedFloor.getCapacity());

        return mapToResponse(savedFloor);
    }


    // get all floors
    @Override
    public List<ParkingFloorResponse> getAllFloors() {

        logger.debug("fetching all floors");
        return parkingFloorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // get floor by id

    public ParkingFloorResponse getFloorById(Long id) {

        logger.debug("fetching floor with floor id {}",id);
        ParkingFloor floor = parkingFloorRepository.findById(id)
                .orElseThrow(() -> {
                        logger.warn("floor not found with floor id {}",id);
                      return   new ResourceNotFoundException("floor not found");
                });

        return mapToResponse(floor);
    }


    //update floors
//                 updateFloor()
//                 Find Floor
//
//                    ↓
//
//                Update Values
//
//                     ↓
//
//                   Save
//
//                   ↓
//
//               Return Updated Floor

    @Override
    public ParkingFloorResponse updateFloor(Long id, CreateParkingFloorRequest request) {

        logger.debug("Fetching floor for update with id {}",id);
        ParkingFloor floor = parkingFloorRepository.findById(id)
                .orElseThrow(() -> {
                        logger.warn("Floor not found with floor id {}",id);
                        return  new ResourceNotFoundException("Floor not found");
    });

        Optional<ParkingFloor> existingFloor =
                parkingFloorRepository.findByFloorNumber(request.getFloorNumber());

        if (existingFloor.isPresent()
                && !existingFloor.get().getId().equals(id)) {
            logger.warn("Vehicle already exists with id  {}",request.getFloorNumber());
            throw new DuplicateResourceException("Floor number already exists.");
        }

        floor.setFloorNumber(request.getFloorNumber());
        floor.setName(request.getName());
        floor.setCapacity(request.getCapacity());

        ParkingFloor updatedFloor = parkingFloorRepository.save(floor);
        logger.info("vehicle update successfully with id {},with floor number {}",
                updatedFloor.getId(),
                updatedFloor.getFloorNumber());

        return mapToResponse(updatedFloor);
    }
    // delete floor

    public void deleteFloor(Long id) {

       logger.debug("Fetching floor with floor id {}",id);
        ParkingFloor parkingFloor = parkingFloorRepository.findById(id)

                .orElseThrow(() -> {

                    logger.warn("floor not found id {}",id);
                    return new ResourceNotFoundException("floor not found");
                });

        parkingFloorRepository.delete(parkingFloor);
        logger.info("Floor deleted successfully with id: {}", id);
    }

    //covert entity to response
    private ParkingFloorResponse mapToResponse(ParkingFloor floor) {

        return ParkingFloorResponse.builder()
                .id(floor.getId())
                .floorNumber((floor.getFloorNumber()))
                .name(floor.getName())
                .capacity(floor.getCapacity())
                .build();
    }

}

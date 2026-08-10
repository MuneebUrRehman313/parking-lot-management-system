package com.muneeb.parkinglot.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.muneeb.parkinglot.dto.request.CreateVehicleRequest;
import com.muneeb.parkinglot.dto.response.VehicleResponse;
import com.muneeb.parkinglot.entity.Vehicle;
import com.muneeb.parkinglot.exception.DuplicateResourceException;
import com.muneeb.parkinglot.exception.ResourceNotFoundException;
import com.muneeb.parkinglot.repository.VehicleRepository;
import com.muneeb.parkinglot.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);
    private final VehicleRepository vehicleRepository;

    @Override
    public VehicleResponse createVehicle(CreateVehicleRequest request) {

        logger.info(
                "Creating vehicle with vehicle number: {}",
                request.getVehicleNumber()
        );

        if (vehicleRepository.existsByVehicleNumber(request.getVehicleNumber())) {

            logger.warn(
                    "Duplicate vehicle registration attempted: {}",
                    request.getVehicleNumber()
            );

            throw new DuplicateResourceException("Vehicle already exists.");
        }

        Vehicle vehicle = Vehicle.builder()
                .vehicleNumber(request.getVehicleNumber())
                .ownerName(request.getOwnerName())
                .vehicleType(request.getVehicleType())
                .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        logger.info(
                "Vehicle created successfully with ID: {} and vehicle number: {}",
                savedVehicle.getId(),
                savedVehicle.getVehicleNumber()
        );

        return mapToResponse(savedVehicle);
    }

    @Override
    public VehicleResponse getVehicleById(Long id) {

        logger.debug("fetching vehicle with id {}",id);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->{
                   logger.warn("vehicle not found with id {}",id);
                   return new ResourceNotFoundException("vehicle not found ");
                });


        return mapToResponse(vehicle);
    }

    @Override
    public List<VehicleResponse> getAllVehicles() {

        logger.debug("Fetching all vehicles");
        return vehicleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public VehicleResponse updateVehicle(Long id, CreateVehicleRequest request) {

        logger.debug("fetching vehicle by id {}",id);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Vehicle not found with id: {}", id);
                    return new ResourceNotFoundException("Vehicle not found.");
                });




        vehicle.setVehicleNumber(request.getVehicleNumber());
        vehicle.setOwnerName(request.getOwnerName());
        vehicle.setVehicleType(request.getVehicleType());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        logger.info("vehicle updated successfully with id :{} and vehicleNumber{} ",
                updatedVehicle.getId(),
                updatedVehicle.getVehicleNumber()
                );
        return mapToResponse(updatedVehicle);
    }

    @Override
    public void deleteVehicle(Long id) {

        logger.debug("vehicle fetching with id {}",id);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() ->{
                    logger.warn("Vehicle not found with id for delete operation {}",id);
                    return new ResourceNotFoundException("vehicle not found ");
                });



        vehicleRepository.delete(vehicle);
        logger.info("vehicle deleted successfully with id {}",id);
    }

    private VehicleResponse mapToResponse(Vehicle vehicle) {

        return VehicleResponse.builder()
                .id(vehicle.getId())
                .vehicleNumber(vehicle.getVehicleNumber())
                .ownerName(vehicle.getOwnerName())
                .vehicleType(vehicle.getVehicleType())
                .build();
    }
}
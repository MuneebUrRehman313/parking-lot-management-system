package com.muneeb.parkinglot.service.impl;

import com.muneeb.parkinglot.dto.request.CreateParkingFloorRequest;
import com.muneeb.parkinglot.dto.response.ParkingFloorResponse;
import com.muneeb.parkinglot.entity.ParkingFloor;
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

    private  final ParkingFloorRepository parkingFloorRepository;


    //create floor
    @Override
   public ParkingFloorResponse createFloor (CreateParkingFloorRequest request){

       //check duplicate
       if(parkingFloorRepository.existsByFloorNumber(request.getFloorNumber())){
           throw  new RuntimeException("floor already exists");
       }

       ParkingFloor parkingFloor = ParkingFloor.builder()
               .floorNumber(request.getFloorNumber())
               .name(request.getName())
               .capacity(request.getCapacity())
               .build();

       ParkingFloor savedFloor = parkingFloorRepository.save(parkingFloor);

       return  mapToResponse(savedFloor);
   }


   // get all floors
    @Override
    public List<ParkingFloorResponse> getAllFloors() {

       return parkingFloorRepository.findAll()
               .stream()
               .map(this::mapToResponse)
               .toList();
    }

    // get floor by id

    public  ParkingFloorResponse getFloorById(Long id){

        ParkingFloor floor = parkingFloorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("floor not found"));

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

        ParkingFloor floor = parkingFloorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Floor not found"));

        Optional<ParkingFloor> existingFloor =
                parkingFloorRepository.findByFloorNumber(request.getFloorNumber());

        if (existingFloor.isPresent()
                && !existingFloor.get().getId().equals(id)) {

            throw new RuntimeException("Floor number already exists.");
        }

        floor.setFloorNumber(request.getFloorNumber());
        floor.setName(request.getName());
        floor.setCapacity(request.getCapacity());

        ParkingFloor updatedFloor = parkingFloorRepository.save(floor);

        return mapToResponse(updatedFloor);
    }
    // delete floor

    public void deleteFloor(Long id){
        ParkingFloor parkingFloor = parkingFloorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("floor not found"));

        parkingFloorRepository.delete(parkingFloor);
    }

    //covert entity to response
    private ParkingFloorResponse mapToResponse(ParkingFloor floor){

       return ParkingFloorResponse.builder()
               .id(floor.getId())
               .floorNumber((floor.getFloorNumber()))
               .name(floor.getName())
               .capacity(floor.getCapacity())
               .build();
    }

}

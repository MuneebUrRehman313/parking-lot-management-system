package com.muneeb.parkinglot.service.impl;

import com.muneeb.parkinglot.dto.request.CreateParkingSpotRequest;
import com.muneeb.parkinglot.dto.response.ParkingSpotResponse;
import com.muneeb.parkinglot.entity.ParkingSpot;
import com.muneeb.parkinglot.enums.ParkingSpotType;
import com.muneeb.parkinglot.repository.ParkingSpotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingSpotServiceImpl {

    @Mock
    private ParkingSpotRepository parkingSpotRepository;

    @InjectMocks
    private ParkingSpotServiceImpl parkingSpotService;


    @Test
    void createParkingSpot_success(){

        // Arrange

        CreateParkingSpotRequest request = new CreateParkingSpotRequest();
        request.setFloorNumber(1);
        request.setSpotNumber("A101");
        request.setSpotType(ParkingSpotType.EV);

        ParkingSpot spot = ParkingSpot.builder()
                .id(1L)
                .spotNumber("A101")
                .spotType(ParkingSpotType.EV)
                .build();

        when(parkingSpotRepository.existsById(1L))
                .thenReturn(false);

        when(parkingSpotRepository.save(any(ParkingSpot.class)))
                .thenReturn(spot);

        //act
        ParkingSpotResponse response = parkingSpotService.createSpot(request);

        //assert
        assertNotNull(response);
        assertEquals(1L,response.getId());
        assertEquals("A101",response.getSpotNumber());
        assertEquals(ParkingSpotType.EV,response.getSpotType());

        //verify
        verify(parkingSpotRepository).findBySpotNumber("A101");
        verify(parkingSpotRepository,never()).save(any(ParkingSpot.class));

    }


}

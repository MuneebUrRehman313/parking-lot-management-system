package com.muneeb.parkinglot.service.impl;

import com.muneeb.parkinglot.dto.request.CreateTicketRequest;
import com.muneeb.parkinglot.dto.response.TicketResponse;
import com.muneeb.parkinglot.entity.ParkingSpot;
import com.muneeb.parkinglot.entity.Ticket;
import com.muneeb.parkinglot.entity.Vehicle;
import com.muneeb.parkinglot.enums.ParkingSpotStatus;
import com.muneeb.parkinglot.enums.ParkingSpotType;
import com.muneeb.parkinglot.enums.TicketStatus;
import com.muneeb.parkinglot.exception.DuplicateResourceException;
import com.muneeb.parkinglot.exception.ResourceNotFoundException;
import com.muneeb.parkinglot.repository.ParkingSpotRepository;
import com.muneeb.parkinglot.repository.TicketRepository;
import com.muneeb.parkinglot.repository.VehicleRepository;
import com.muneeb.parkinglot.service.FeeCalculationService;
import com.muneeb.parkinglot.service.TicketService;
import com.muneeb.parkinglot.service.factory.TicketFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl  implements TicketService {

    private  final TicketRepository ticketRepository;
    private  final VehicleRepository vehicleRepository;
    private  final ParkingSpotRepository parkingSpotRepository;
    private final FeeCalculationService feeCalculationService;
    private final TicketFactory ticketFactory;
    // create ticker
    public TicketResponse createTicket(CreateTicketRequest request){

        // 1 fine vehicle
       Vehicle vehicle =  vehicleRepository
                .findById(request.getVehicleId())
                .orElseThrow(()-> new ResourceNotFoundException("vehicle not found"));

       //check active ticket
       ticketRepository
               .findByVehicleAndStatus(
               vehicle,
               TicketStatus.ACTIVE
       ).ifPresent(ticket -> {
           throw  new DuplicateResourceException("vehicle is already parked");
               });

       // 3 convert vehicleType -> parkingSpotType
        ParkingSpotType requiredSpotType = switch (vehicle.getVehicleType()) {
            case CAR -> ParkingSpotType.CAR;
            case BIKE -> ParkingSpotType.BIKE;
            case TRUCK -> ParkingSpotType.TRUCK;
            case EV -> ParkingSpotType.EV;
        };

            //4 FIND AVAILABLE SPOT
        ParkingSpot Spot = parkingSpotRepository
                .findFirstBySpotTypeAndStatus(
                        requiredSpotType,
                        ParkingSpotStatus.AVAILABLE
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException("No parking spot available"));
        //5 occupy spot
        Spot.setStatus(ParkingSpotStatus.OCCUPIED);
        parkingSpotRepository.save(Spot);

        //6 create ticket
        ParkingSpot spot = null;
        Ticket ticket = ticketFactory.createTicket(
                vehicle,
                spot
        );

        Ticket savedTicket = ticketRepository.save(ticket);

        return mapToResponse(savedTicket);
    }



    @Override
    public List<TicketResponse> getAllTickets() {
        return ticketRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

//        ;
    }

    public TicketResponse getTicketById(Long id){

            Ticket ticket =   ticketRepository.findById(id).orElseThrow(()-> new RuntimeException("ticket not found"));

            return  mapToResponse(ticket);
//        return  null;
    }

    public TicketResponse completeTicket(Long id){

        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new RuntimeException("ticket not found"));


        ticket.setExitTime(LocalDateTime.now());

        Double amount = feeCalculationService.calculateFee(ticket);

        ticket.setAmount(amount);


        ticket.setStatus(TicketStatus.COMPLETED);

        ParkingSpot spot = ticket.getParkingSpot();
        spot.setStatus(ParkingSpotStatus.AVAILABLE);

        parkingSpotRepository.save(spot);
        Ticket updataTicket = ticketRepository.save(ticket);

        return mapToResponse(updataTicket);
//        return  null;
    }

    // convert itno jsom form

    public TicketResponse mapToResponse(Ticket ticket){

        return TicketResponse.builder()
                .id(ticket.getId())
                .vehicleNumber(ticket.getVehicle().getVehicleNumber())
                .spotNumber(ticket.getParkingSpot().getSpotNumber())
                .entryTime(ticket.getEntryTime())
                .exitTime(ticket.getExitTime())
                .amount(ticket.getAmount())
                .status(ticket.getStatus())
                .build();
    }
}

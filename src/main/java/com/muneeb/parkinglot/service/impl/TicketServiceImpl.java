package com.muneeb.parkinglot.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    private  final TicketRepository ticketRepository;
    private  final VehicleRepository vehicleRepository;
    private  final ParkingSpotRepository parkingSpotRepository;
    private final FeeCalculationService feeCalculationService;
    private final TicketFactory ticketFactory;
    // create ticker
    public TicketResponse createTicket(CreateTicketRequest request){

        logger.debug("creating ticket with vehicle id {}",request.getVehicleId());
        // 1 fine vehicle
       Vehicle vehicle =  vehicleRepository
                .findById(request.getVehicleId())
                .orElseThrow(()->{
                  logger.warn("vehicle not found with vehicle id {}",request.getVehicleId());
         return new ResourceNotFoundException("vehicle not found");
                });

       //check active ticket
       ticketRepository
               .findByVehicleAndStatus(
               vehicle,
               TicketStatus.ACTIVE
       ).ifPresent(ticket -> {
           logger.warn("active ticket already exists for vehicle id:{}",vehicle.getId());
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
                .orElseThrow(() ->{

                    logger.warn(
                            "No parking spot available for vehicle id: {} and required spot type: {}",
                            vehicle.getId(),
                            requiredSpotType
                    );
                      return  new ResourceNotFoundException("No parking spot available");});
        //5 occupy spot
        Spot.setStatus(ParkingSpotStatus.OCCUPIED);
        parkingSpotRepository.save(Spot);


        //6 create ticket

        Ticket ticket = ticketFactory.createTicket(
                vehicle,
                Spot
        );

        Ticket savedTicket = ticketRepository.save(ticket);
        logger.info("ticket created succesfully with id :{} and vehicle and id  : {} and spot id {},and spotType {}",
                savedTicket.getId(),
                vehicle.getId(),
                Spot.getId(),
                Spot.getSpotType()
                );

        return mapToResponse(savedTicket);
    }



    @Override
    public List<TicketResponse> getAllTickets() {
       logger.debug("fetching all tickets");
        return ticketRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

//        ;
    }

    public TicketResponse getTicketById(Long id){

            logger.debug("fetching tickets by id {}",id);
            Ticket ticket =   ticketRepository.findById(id).orElseThrow(()-> {

                logger.warn("ticket not found with id {}",id);
                 return new ResourceNotFoundException("ticket not found");
            });

            return  mapToResponse(ticket);
//        return  null;
    }

    public TicketResponse completeTicket(Long id){

        logger.debug("fetching ticket for complete {} ",id);
        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->{

            logger.warn("Ticket not found with id {}",id);
           return new ResourceNotFoundException("ticket not found");
        });


        ticket.setExitTime(LocalDateTime.now());

        Double amount = feeCalculationService.calculateFee(ticket);
        logger.debug(
                "Calculated parking fee for ticket id: {} is: {}",
                ticket.getId(),
                amount
        );
        ticket.setAmount(amount);


        ticket.setStatus(TicketStatus.COMPLETED);
        Vehicle vehicle = ticket.getVehicle();
        ParkingSpot spot = ticket.getParkingSpot();
        spot.setStatus(ParkingSpotStatus.AVAILABLE);

        parkingSpotRepository.save(spot);
        Ticket updataTicket = ticketRepository.save(ticket);

        logger.info("ticket completed fully with id :{} and spot  id :{} and and vehicle id {} ",
                updataTicket.getId(),
                spot.getId(),
                vehicle.getId());

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

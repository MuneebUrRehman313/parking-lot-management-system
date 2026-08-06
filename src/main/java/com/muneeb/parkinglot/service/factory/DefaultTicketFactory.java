package com.muneeb.parkinglot.service.factory;

import com.muneeb.parkinglot.entity.ParkingSpot;
import com.muneeb.parkinglot.entity.Ticket;
import com.muneeb.parkinglot.entity.Vehicle;
import com.muneeb.parkinglot.enums.TicketStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class DefaultTicketFactory  implements TicketFactory{

    @Override
    public Ticket createTicket(Vehicle vehicle, ParkingSpot parkingSpot) {
        return Ticket.builder()
                .vehicle(vehicle)
                .parkingSpot(parkingSpot)
                .entryTime(LocalDateTime.now())
                .status(TicketStatus.ACTIVE)
                .build();
    }
}

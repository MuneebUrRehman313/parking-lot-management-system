package com.muneeb.parkinglot.service.factory;

import com.muneeb.parkinglot.entity.ParkingSpot;
import com.muneeb.parkinglot.entity.Ticket;
import com.muneeb.parkinglot.entity.Vehicle;
import com.muneeb.parkinglot.enums.ParkingSpotType;

public interface TicketFactory {

    Ticket createTicket(
            Vehicle vehicle,
            ParkingSpot parkingSpot
    );
}

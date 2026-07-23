package com.muneeb.parkinglot.repository;

import com.muneeb.parkinglot.entity.Ticket;
import com.muneeb.parkinglot.entity.Vehicle;
import com.muneeb.parkinglot.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket ,Long> {


//    Ye method check karega:
//
//    Kya is vehicle ka koi ACTIVE ticket already hai?
//
//    Example:
//
//    Vehicle
//
//            MP09AB1234
//
//    Agar uska ACTIVE ticket mil gaya,
//
//    to naya ticket create nahi karenge.
//
//    Kyuki ek vehicle ek time par sirf ek hi jagah park ho sakti hai.
//

    Optional<Ticket> findByVehicleAndStatus(
            Vehicle vehicle,
            TicketStatus status
    );
}

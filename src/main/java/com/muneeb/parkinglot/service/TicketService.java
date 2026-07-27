package com.muneeb.parkinglot.service;

import com.muneeb.parkinglot.dto.request.CreateTicketRequest;
import com.muneeb.parkinglot.dto.response.TicketResponse;
import com.muneeb.parkinglot.repository.TicketRepository;

import java.util.List;

public interface TicketService {

    TicketResponse createTicket(CreateTicketRequest request);

    List<TicketResponse> getAllTickets();

    TicketResponse getTicketById(Long id);

    TicketResponse completeTicket(Long id);
}

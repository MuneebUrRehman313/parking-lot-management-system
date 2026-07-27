package com.muneeb.parkinglot.controller;

import com.muneeb.parkinglot.dto.request.CreateTicketRequest;
import com.muneeb.parkinglot.dto.response.TicketResponse;
import com.muneeb.parkinglot.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;


@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;



    @PostMapping
    public TicketResponse createTicket(@Valid @RequestBody CreateTicketRequest request){
   return ticketService.createTicket(request);
    }


    @GetMapping
    public List<TicketResponse> getAllTicket() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketResponse getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PutMapping("/{id}/complete")
    public TicketResponse compelteTicket(@PathVariable Long id){
        return ticketService.completeTicket(id);
    }

}



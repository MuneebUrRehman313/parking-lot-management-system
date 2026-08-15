package com.muneeb.parkinglot.controller;

import com.muneeb.parkinglot.dto.request.CreateTicketRequest;
import com.muneeb.parkinglot.dto.response.TicketResponse;
import com.muneeb.parkinglot.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
@Tag(
        name = "Ticket APIs",
        description = "APIs for managing parking tickets"
)
public class TicketController {

    private final TicketService ticketService;


    @Operation(
            summary = "Create a parking ticket",
            description = "Creates a new parking ticket and automatically allocates an available parking spot"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ticket data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Vehicle or parking spot not found"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Vehicle already has an active ticket"
            )
    })
    @PostMapping
    public TicketResponse createTicket(
            @Valid @RequestBody CreateTicketRequest request) {

        return ticketService.createTicket(request);
    }


    @Operation(
            summary = "Get all parking tickets",
            description = "Retrieves all parking tickets from the system"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Tickets retrieved successfully"
    )
    @GetMapping
    public List<TicketResponse> getAllTicket() {

        return ticketService.getAllTickets();
    }


    @Operation(
            summary = "Get ticket by ID",
            description = "Retrieves a parking ticket using its unique ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ticket not found"
            )
    })
    @GetMapping("/{id}")
    public TicketResponse getTicketById(
            @PathVariable Long id) {

        return ticketService.getTicketById(id);
    }


    @Operation(
            summary = "Complete parking ticket",
            description = "Completes an active parking ticket, calculates the parking fee, and releases the parking spot"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ticket completed successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ticket not found"
            )
    })
    @PutMapping("/{id}/complete")
    public TicketResponse completeTicket(
            @PathVariable Long id) {

        return ticketService.completeTicket(id);
    }
}
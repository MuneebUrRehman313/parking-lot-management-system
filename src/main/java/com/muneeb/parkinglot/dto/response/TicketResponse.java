package com.muneeb.parkinglot.dto.response;

import com.muneeb.parkinglot.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TicketResponse {

    private Long id ;

    private String vehicleNumber;

    private String spotNumber;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    private Double amount;

    private TicketStatus status;
}

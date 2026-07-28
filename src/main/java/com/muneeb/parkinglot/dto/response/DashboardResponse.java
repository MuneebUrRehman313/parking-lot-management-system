package com.muneeb.parkinglot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private long allFloors;

    private long allSpots;

    private long availableSpots;

    private long occupiedSpots;

    private long activeTickets;

}

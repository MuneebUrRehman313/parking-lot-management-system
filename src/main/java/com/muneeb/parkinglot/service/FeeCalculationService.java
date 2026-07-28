package com.muneeb.parkinglot.service;

import com.muneeb.parkinglot.entity.Ticket;

public interface FeeCalculationService {

    Double calculateFee(Ticket ticket);
}

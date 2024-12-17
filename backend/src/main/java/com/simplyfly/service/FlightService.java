package com.simplyfly.service;

import com.simplyfly.dto.FlightDTO;
import com.simplyfly.entity.Flight;

import java.util.List;

public interface FlightService {
    Flight createFlight(Flight flight);
    Flight getFlightById(Integer flightId);
    List<FlightDTO> getAllFlights();
    Flight updateFlight(Integer flightId, Flight flight);
    void deleteFlight(Integer flightId);
}

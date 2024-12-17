package com.simplyfly.controller;

import com.simplyfly.dto.FlightDTO;
import com.simplyfly.entity.Flight;
import com.simplyfly.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private static final Logger logger = LoggerFactory.getLogger(FlightController.class);

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        logger.info("Creating flight: {}", flight);
        Flight createdFlight = flightService.createFlight(flight);
        logger.info("Flight created successfully: {}", createdFlight);
        return ResponseEntity.ok(createdFlight);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable Integer id) {
        logger.info("Fetching flight with ID: {}", id);
        Flight flight = flightService.getFlightById(id);
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightName(flight.getFlightName());
        flightDTO.setFlightNumber(flight.getFlightNumber());
        flightDTO.setTotalSeats(flight.getTotalSeats());
        flightDTO.setFare(flight.getFare());
        flightDTO.setBaggageInfo(flight.getBaggageInfo());
        flightDTO.setOwnerName(flight.getOwner() != null ? flight.getOwner().getUsername() : null);

        logger.info("Fetched flight details: {}", flightDTO);
        return ResponseEntity.ok(flightDTO);
    }

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getAllFlights() {
        logger.info("Fetching all flights");
        List<FlightDTO> flights = flightService.getAllFlights();
        logger.info("Fetched {} flights", flights.size());
        return ResponseEntity.ok(flights);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Integer id, @RequestBody Flight flight) {
        logger.info("Updating flight with ID: {}", id);
        Flight updatedFlight = flightService.updateFlight(id, flight);
        logger.info("Flight updated successfully: {}", updatedFlight);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Integer id) {
        logger.info("Deleting flight with ID: {}", id);
        flightService.deleteFlight(id);
        logger.info("Flight with ID: {} deleted successfully", id);
        return ResponseEntity.ok("Flight deleted successfully");
    }
}

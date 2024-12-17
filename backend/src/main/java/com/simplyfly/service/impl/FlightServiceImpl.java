package com.simplyfly.service.impl;

import com.simplyfly.dto.FlightDTO;
import com.simplyfly.entity.Flight;
import com.simplyfly.exception.DuplicateResourceException;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public Flight createFlight(Flight flight) {
        logger.info("Attempting to create flight with number: {}", flight.getFlightNumber());

       
        if (flightRepository.existsByFlightNumber(flight.getFlightNumber())) {
            logger.error("Flight with number {} already exists.", flight.getFlightNumber());
            throw new DuplicateResourceException("Flight with number " + flight.getFlightNumber() + " already exists.");
        }

        Flight savedFlight = flightRepository.save(flight);
        logger.info("Flight created successfully with ID: {}", savedFlight.getFlightId());
        return savedFlight;
    }

    @Override
    public Flight getFlightById(Integer flightId) {
        logger.debug("Fetching flight with ID: {}", flightId);

        return flightRepository.findById(flightId).orElseThrow(() -> {
            logger.warn("Flight with ID {} not found.", flightId);
            return new ResourceNotFoundException("Flight with ID " + flightId + " not found.");
        });
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        logger.debug("Fetching all flights.");

        List<FlightDTO> flights = flightRepository.findAll().stream().map(flight -> {
            FlightDTO dto = new FlightDTO();
            dto.setFlightName(flight.getFlightName());
            dto.setFlightNumber(flight.getFlightNumber());
            dto.setTotalSeats(flight.getTotalSeats());
            dto.setFare(flight.getFare());
            dto.setBaggageInfo(flight.getBaggageInfo());
            dto.setOwnerName(flight.getOwner() != null ? flight.getOwner().getUsername() : null);
            return dto;
        }).collect(Collectors.toList());

        logger.info("Successfully fetched {} flights.", flights.size());
        return flights;
    }

    @Override
    public Flight updateFlight(Integer flightId, Flight updatedFlight) {
        logger.info("Updating flight with ID: {}", flightId);

        
        Flight flight = getFlightById(flightId);


        if (!flight.getFlightNumber().equals(updatedFlight.getFlightNumber()) &&
                flightRepository.existsByFlightNumber(updatedFlight.getFlightNumber())) {
            logger.error("Flight with number {} already exists.", updatedFlight.getFlightNumber());
            throw new DuplicateResourceException("Flight with number " + updatedFlight.getFlightNumber() + " already exists.");
        }

        flight.setFlightName(updatedFlight.getFlightName());
        flight.setFlightNumber(updatedFlight.getFlightNumber());
        flight.setTotalSeats(updatedFlight.getTotalSeats());
        flight.setFare(updatedFlight.getFare());
        flight.setBaggageInfo(updatedFlight.getBaggageInfo());
        flight.setOwner(updatedFlight.getOwner());

        Flight savedFlight = flightRepository.save(flight);
        logger.info("Flight with ID: {} updated successfully.", flightId);
        return savedFlight;
    }

    @Override
    public void deleteFlight(Integer flightId) {
        logger.info("Attempting to delete flight with ID: {}", flightId);

        
        Flight flight = getFlightById(flightId);
        flightRepository.delete(flight);

        logger.info("Flight with ID: {} deleted successfully.", flightId);
    }
}

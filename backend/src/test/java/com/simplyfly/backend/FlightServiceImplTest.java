package com.simplyfly.backend;

import com.simplyfly.dto.FlightDTO;
import com.simplyfly.entity.Flight;
import com.simplyfly.exception.DuplicateResourceException;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.service.impl.FlightServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito annotations
public class FlightServiceImplTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightServiceImpl flightService; // The service class we're testing

    private Flight flight;
    private FlightDTO flightDTO;

    @BeforeEach
    public void setUp() {
        // Create a sample flight object for testing
        flight = new Flight();
        flight.setFlightName("Test Flight");
        flight.setFlightNumber("12345");
        flight.setTotalSeats(150);
        flight.setFare(new BigDecimal("500.00"));  // Set fare as BigDecimal
        flight.setBaggageInfo("20kg");

        flightDTO = new FlightDTO();
        flightDTO.setFlightName(flight.getFlightName());
        flightDTO.setFlightNumber(flight.getFlightNumber());
        flightDTO.setTotalSeats(flight.getTotalSeats());
        flightDTO.setFare(flight.getFare());
        flightDTO.setBaggageInfo(flight.getBaggageInfo());
    }

    @Test
    public void testCreateFlight_ShouldCreateFlight() {
        when(flightRepository.existsByFlightNumber(flight.getFlightNumber())).thenReturn(false);
        when(flightRepository.save(flight)).thenReturn(flight);

        Flight createdFlight = flightService.createFlight(flight);

        assertNotNull(createdFlight);
        assertEquals(flight.getFlightName(), createdFlight.getFlightName());
        assertEquals(flight.getFare(), createdFlight.getFare()); // Comparing BigDecimal properly
        verify(flightRepository, times(1)).save(flight); // Verifying that save was called once
    }

    @Test
    public void testCreateFlight_ShouldThrowDuplicateResourceException_WhenFlightNumberExists() {
        when(flightRepository.existsByFlightNumber(flight.getFlightNumber())).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () -> flightService.createFlight(flight));
        assertEquals("Flight with number 12345 already exists.", exception.getMessage());
        verify(flightRepository, never()).save(flight); // Verifying that save is never called
    }

    @Test
    public void testGetFlightById_ShouldReturnFlight() {
        when(flightRepository.findById(1)).thenReturn(Optional.of(flight));

        Flight foundFlight = flightService.getFlightById(1);

        assertNotNull(foundFlight);
        assertEquals(flight.getFlightName(), foundFlight.getFlightName());
        assertEquals(flight.getFare(), foundFlight.getFare()); // Comparing BigDecimal properly
    }

    @Test
    public void testGetFlightById_ShouldThrowResourceNotFoundException_WhenFlightNotFound() {
        when(flightRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> flightService.getFlightById(1));
        assertEquals("Flight with ID 1 not found.", exception.getMessage());
    }

    @Test
    public void testGetAllFlights_ShouldReturnListOfFlights() {
        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight));

        var flights = flightService.getAllFlights();

        assertNotNull(flights);
        assertFalse(flights.isEmpty());
        assertEquals(1, flights.size());
        assertEquals(flight.getFlightName(), flights.get(0).getFlightName());
        assertEquals(flight.getFare(), flights.get(0).getFare()); // Comparing BigDecimal properly
    }

    @Test
    public void testUpdateFlight_ShouldUpdateFlight() {
        Flight updatedFlight = new Flight();
        updatedFlight.setFlightName("Updated Flight");
        updatedFlight.setFlightNumber("12345");
        updatedFlight.setTotalSeats(180);
        updatedFlight.setFare(new BigDecimal("550.00"));  // Set fare as BigDecimal
        updatedFlight.setBaggageInfo("25kg");

        when(flightRepository.findById(1)).thenReturn(Optional.of(flight));
        when(flightRepository.existsByFlightNumber(updatedFlight.getFlightNumber())).thenReturn(false);
        when(flightRepository.save(updatedFlight)).thenReturn(updatedFlight);

        Flight result = flightService.updateFlight(1, updatedFlight);

        assertNotNull(result);
        assertEquals(updatedFlight.getFlightName(), result.getFlightName());
        assertEquals(updatedFlight.getFare(), result.getFare()); // Comparing BigDecimal properly
        verify(flightRepository, times(1)).save(updatedFlight);
    }

    @Test
    public void testUpdateFlight_ShouldThrowDuplicateResourceException_WhenFlightNumberExists() {
        Flight updatedFlight = new Flight();
        updatedFlight.setFlightNumber("12345");  // Same as the original flight number

        when(flightRepository.findById(1)).thenReturn(Optional.of(flight));
        when(flightRepository.existsByFlightNumber(updatedFlight.getFlightNumber())).thenReturn(true);

        DuplicateResourceException exception = assertThrows(DuplicateResourceException.class, () -> flightService.updateFlight(1, updatedFlight));
        assertEquals("Flight with number 12345 already exists.", exception.getMessage());
        verify(flightRepository, never()).save(updatedFlight);  // Verifying that save was not called
    }

    @Test
    public void testDeleteFlight_ShouldDeleteFlight() {
        when(flightRepository.findById(1)).thenReturn(Optional.of(flight));

        flightService.deleteFlight(1);

        verify(flightRepository, times(1)).delete(flight);  // Verifying that delete was called once
    }

    @Test
    public void testDeleteFlight_ShouldThrowResourceNotFoundException_WhenFlightNotFound() {
        when(flightRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> flightService.deleteFlight(1));
        assertEquals("Flight with ID 1 not found.", exception.getMessage());
    }
}

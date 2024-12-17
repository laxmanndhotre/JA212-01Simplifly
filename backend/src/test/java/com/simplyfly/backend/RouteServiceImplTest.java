package com.simplyfly.backend;

import com.simplyfly.dto.RouteDto;
import com.simplyfly.entity.Flight;
import com.simplyfly.entity.Route;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.repository.RouteRepository;
import com.simplyfly.service.impl.RouteServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Enables Mockito for unit tests
public class RouteServiceImplTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private RouteServiceImpl routeService; // The service class we're testing

    private RouteDto routeDto;
    private Route route;
    private Flight flight;

    @BeforeEach
    public void setUp() {
        // Create a flight object for testing
        flight = new Flight();
        flight.setFlightId(1);
        flight.setFlightName("Test Flight");

        // Create a RouteDto object for testing
        routeDto = new RouteDto();
        routeDto.setFlightId(flight.getFlightId());
        routeDto.setOrigin("New York");
        routeDto.setDestination("Los Angeles");
        routeDto.setDepartureTime(LocalDateTime.now().plusHours(1));
        routeDto.setArrivalTime(LocalDateTime.now().plusHours(5));
        routeDto.setAvailableSeats(150);

        // Create a Route object for testing
        route = new Route();
        route.setRouteId(1);
        route.setFlight(flight);
        route.setOrigin("New York");
        route.setDestination("Los Angeles");
        route.setDepartureTime(routeDto.getDepartureTime());
        route.setArrivalTime(routeDto.getArrivalTime());
        route.setAvailableSeats(routeDto.getAvailableSeats());
    }

    @Test
    public void testAddRoute_ShouldAddRoute() {
        when(flightRepository.findById(routeDto.getFlightId())).thenReturn(Optional.of(flight));
        when(routeRepository.save(any(Route.class))).thenReturn(route);

        RouteDto createdRoute = routeService.addRoute(routeDto);

        assertNotNull(createdRoute);
        assertEquals(routeDto.getOrigin(), createdRoute.getOrigin());
        assertEquals(routeDto.getDestination(), createdRoute.getDestination());
        assertEquals(routeDto.getAvailableSeats(), createdRoute.getAvailableSeats());
        verify(routeRepository, times(1)).save(any(Route.class)); // Verifying that save was called once
    }

    @Test
    public void testAddRoute_ShouldThrowResourceNotFoundException_WhenFlightNotFound() {
        when(flightRepository.findById(routeDto.getFlightId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> routeService.addRoute(routeDto));
        assertEquals("Flight with ID 1 not found", exception.getMessage());
        verify(routeRepository, never()).save(any(Route.class)); // Verifying that save was never called
    }

    @Test
    public void testGetRouteById_ShouldReturnRoute() {
        when(routeRepository.findById(1)).thenReturn(Optional.of(route));

        RouteDto foundRoute = routeService.getRouteById(1);

        assertNotNull(foundRoute);
        assertEquals(route.getRouteId(), foundRoute.getRouteId());
        assertEquals(route.getOrigin(), foundRoute.getOrigin());
    }

    @Test
    public void testGetRouteById_ShouldThrowResourceNotFoundException_WhenRouteNotFound() {
        when(routeRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> routeService.getRouteById(1));
        assertEquals("Route with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testGetAllRoutes_ShouldReturnListOfRoutes() {
        when(routeRepository.findAll()).thenReturn(Arrays.asList(route));

        var routes = routeService.getAllRoutes();

        assertNotNull(routes);
        assertFalse(routes.isEmpty());
        assertEquals(1, routes.size());
        assertEquals(route.getOrigin(), routes.get(0).getOrigin());
    }

    @Test
    public void testUpdateRoute_ShouldUpdateRoute() {
        RouteDto updatedRouteDto = new RouteDto();
        updatedRouteDto.setOrigin("Chicago");
        updatedRouteDto.setDestination("Los Angeles");
        updatedRouteDto.setDepartureTime(LocalDateTime.now().plusHours(2));
        updatedRouteDto.setArrivalTime(LocalDateTime.now().plusHours(6));
        updatedRouteDto.setAvailableSeats(180);

        when(routeRepository.findById(1)).thenReturn(Optional.of(route));
        when(routeRepository.save(any(Route.class))).thenReturn(route);

        RouteDto result = routeService.updateRoute(1, updatedRouteDto);

        assertNotNull(result);
        assertEquals(updatedRouteDto.getOrigin(), result.getOrigin());
        assertEquals(updatedRouteDto.getAvailableSeats(), result.getAvailableSeats());
        verify(routeRepository, times(1)).save(any(Route.class)); // Verifying that save was called once
    }

    @Test
    public void testUpdateRoute_ShouldThrowResourceNotFoundException_WhenRouteNotFound() {
        RouteDto updatedRouteDto = new RouteDto();
        updatedRouteDto.setOrigin("Chicago");

        when(routeRepository.findById(1)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> routeService.updateRoute(1, updatedRouteDto));
        assertEquals("Route with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testDeleteRoute_ShouldDeleteRoute() {
        when(routeRepository.existsById(1)).thenReturn(true);

        routeService.deleteRoute(1);

        verify(routeRepository, times(1)).deleteById(1); // Verifying that deleteById was called once
    }

    @Test
    public void testDeleteRoute_ShouldThrowResourceNotFoundException_WhenRouteNotFound() {
        when(routeRepository.existsById(1)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> routeService.deleteRoute(1));
        assertEquals("Route with ID 1 not found", exception.getMessage());
    }
}

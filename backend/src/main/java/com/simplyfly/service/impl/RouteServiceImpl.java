package com.simplyfly.service.impl;

import com.simplyfly.dto.RouteDto;
import com.simplyfly.entity.Flight;
import com.simplyfly.entity.Route;
import com.simplyfly.exception.ResourceNotFoundException;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.repository.RouteRepository;
import com.simplyfly.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {

    private static final Logger logger = LoggerFactory.getLogger(RouteServiceImpl.class);

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public RouteDto addRoute(RouteDto routeDto) {
        logger.info("Attempting to add a new route from {} to {}.", routeDto.getOrigin(), routeDto.getDestination());

        Flight flight = flightRepository.findById(routeDto.getFlightId())
                .orElseThrow(() -> {
                    logger.warn("Flight with ID {} not found while adding route.", routeDto.getFlightId());
                    return new ResourceNotFoundException("Flight with ID " + routeDto.getFlightId() + " not found");
                });

        Route route = new Route();
        route.setFlight(flight);
        route.setOrigin(routeDto.getOrigin());
        route.setDestination(routeDto.getDestination());
        route.setDepartureTime(routeDto.getDepartureTime());
        route.setArrivalTime(routeDto.getArrivalTime());
        route.setAvailableSeats(routeDto.getAvailableSeats());
        route = routeRepository.save(route);

        logger.info("Route added successfully with ID: {}.", route.getRouteId());
        routeDto.setRouteId(route.getRouteId());
        return routeDto;
    }

    @Override
    public RouteDto getRouteById(int routeId) {
        logger.debug("Fetching route with ID: {}.", routeId);

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> {
                    logger.warn("Route with ID {} not found.", routeId);
                    return new ResourceNotFoundException("Route with ID " + routeId + " not found");
                });

        logger.info("Successfully fetched route with ID: {}.", routeId);
        return mapToDto(route);
    }

    @Override
    public List<RouteDto> getAllRoutes() {
        logger.debug("Fetching all routes.");

        List<RouteDto> routes = routeRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());

        logger.info("Successfully fetched {} routes.", routes.size());
        return routes;
    }

    @Override
    public RouteDto updateRoute(int routeId, RouteDto routeDto) {
        logger.info("Updating route with ID: {}.", routeId);

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> {
                    logger.warn("Route with ID {} not found while attempting update.", routeId);
                    return new ResourceNotFoundException("Route with ID " + routeId + " not found");
                });

        route.setOrigin(routeDto.getOrigin());
        route.setDestination(routeDto.getDestination());
        route.setDepartureTime(routeDto.getDepartureTime());
        route.setArrivalTime(routeDto.getArrivalTime());
        route.setAvailableSeats(routeDto.getAvailableSeats());
        routeRepository.save(route);

        logger.info("Route with ID: {} updated successfully.", routeId);
        return routeDto;
    }

    @Override
    public void deleteRoute(int routeId) {
        logger.info("Attempting to delete route with ID: {}.", routeId);

        if (!routeRepository.existsById(routeId)) {
            logger.warn("Route with ID {} not found while attempting deletion.", routeId);
            throw new ResourceNotFoundException("Route with ID " + routeId + " not found");
        }

        routeRepository.deleteById(routeId);
        logger.info("Route with ID: {} deleted successfully.", routeId);
    }

    private RouteDto mapToDto(Route route) {
        RouteDto routeDto = new RouteDto();
        routeDto.setRouteId(route.getRouteId());
        routeDto.setFlightId(route.getFlight().getFlightId());
        routeDto.setOrigin(route.getOrigin());
        routeDto.setDestination(route.getDestination());
        routeDto.setDepartureTime(route.getDepartureTime());
        routeDto.setArrivalTime(route.getArrivalTime());
        routeDto.setAvailableSeats(route.getAvailableSeats());
        return routeDto;
    }
}  

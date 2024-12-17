package com.simplyfly.controller;

import com.simplyfly.dto.RouteDto;
import com.simplyfly.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteDto> addRoute(@RequestBody RouteDto routeDto) {
        logger.info("Adding new route: {}", routeDto);
        RouteDto addedRoute = routeService.addRoute(routeDto);
        logger.info("Route added successfully: {}", addedRoute);
        return ResponseEntity.ok(addedRoute);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> getRouteById(@PathVariable int id) {
        logger.info("Fetching route with ID: {}", id);
        RouteDto route = routeService.getRouteById(id);
        logger.info("Fetched route details: {}", route);
        return ResponseEntity.ok(route);
    }

    @GetMapping
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        logger.info("Fetching all routes");
        List<RouteDto> routes = routeService.getAllRoutes();
        logger.info("Fetched {} routes", routes.size());
        return ResponseEntity.ok(routes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteDto> updateRoute(@PathVariable int id, @RequestBody RouteDto routeDto) {
        logger.info("Updating route with ID: {}", id);
        RouteDto updatedRoute = routeService.updateRoute(id, routeDto);
        logger.info("Route updated successfully: {}", updatedRoute);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable int id) {
        logger.info("Deleting route with ID: {}", id);
        routeService.deleteRoute(id);
        logger.info("Route with ID: {} deleted successfully", id);
        return ResponseEntity.ok("Route deleted successfully");
    }
}

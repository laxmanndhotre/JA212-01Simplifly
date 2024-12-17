package com.simplyfly.service;

import com.simplyfly.dto.RouteDto;
import java.util.List;

public interface RouteService {
    RouteDto addRoute(RouteDto routeDto);
    RouteDto getRouteById(int routeId);
    List<RouteDto> getAllRoutes();
    RouteDto updateRoute(int routeId, RouteDto routeDto);
    void deleteRoute(int routeId);
}

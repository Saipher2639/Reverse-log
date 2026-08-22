package com.rlo.reverselogisticsoptimizer.service;

import com.rlo.reverselogisticsoptimizer.entity.Route;
import com.rlo.reverselogisticsoptimizer.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route createRoute(Route route) {

        route.setTotalCost(
                route.getTransportCost() + route.getProcessingCost()
        );

        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
}
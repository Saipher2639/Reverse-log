package com.rlo.reverselogisticsoptimizer.controller;

import com.rlo.reverselogisticsoptimizer.entity.Facility;
import com.rlo.reverselogisticsoptimizer.entity.Return;
import com.rlo.reverselogisticsoptimizer.entity.Vehicle;
import com.rlo.reverselogisticsoptimizer.optimization.MultiHopOptimizer;
import com.rlo.reverselogisticsoptimizer.optimization.MultiHopRouteResult;
import com.rlo.reverselogisticsoptimizer.repository.FacilityRepository;
import com.rlo.reverselogisticsoptimizer.repository.ReturnRepository;
import com.rlo.reverselogisticsoptimizer.repository.VehicleRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final ReturnRepository returnRepository;
    private final FacilityRepository facilityRepository;
    private final VehicleRepository vehicleRepository;
    private final MultiHopOptimizer multiHopOptimizer;

    public RouteController(
            ReturnRepository returnRepository,
            FacilityRepository facilityRepository,
            VehicleRepository vehicleRepository,
            MultiHopOptimizer multiHopOptimizer
    ) {
        this.returnRepository = returnRepository;
        this.facilityRepository = facilityRepository;
        this.vehicleRepository = vehicleRepository;
        this.multiHopOptimizer = multiHopOptimizer;
    }

    @PostMapping("/multi-hop")
    public MultiHopRouteResult createRoute(
            @RequestParam String returnId,
            @RequestParam String collectionId,
            @RequestParam String recoveryId,
            @RequestParam String vehicleId
    ) {

        Return returnRequest =
                returnRepository.findById(returnId)
                        .orElseThrow(() ->
                                new RuntimeException("Return not found"));

        Facility collectionCenter =
                facilityRepository.findById(collectionId)
                        .orElseThrow(() ->
                                new RuntimeException("Collection center not found"));

        Facility recoveryCenter =
                facilityRepository.findById(recoveryId)
                        .orElseThrow(() ->
                                new RuntimeException("Recovery center not found"));

        Vehicle vehicle =
                vehicleRepository.findById(vehicleId)
                        .orElseThrow(() ->
                                new RuntimeException("Vehicle not found"));

        return multiHopOptimizer.buildRoute(
                returnRequest,
                collectionCenter,
                recoveryCenter,
                vehicle
        );
    }
}
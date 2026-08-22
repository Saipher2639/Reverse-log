package com.rlo.reverselogisticsoptimizer.controller;

import com.rlo.reverselogisticsoptimizer.entity.Facility;
import com.rlo.reverselogisticsoptimizer.entity.OptimizationHistory;
import com.rlo.reverselogisticsoptimizer.entity.Return;
import com.rlo.reverselogisticsoptimizer.entity.Vehicle;
import com.rlo.reverselogisticsoptimizer.enums.ProductCondition;
import com.rlo.reverselogisticsoptimizer.enums.ReturnStatus;
import com.rlo.reverselogisticsoptimizer.optimization.CostCalculator;
import com.rlo.reverselogisticsoptimizer.optimization.OptimizationResult;
import com.rlo.reverselogisticsoptimizer.optimization.RouteOptimizer;
import com.rlo.reverselogisticsoptimizer.repository.FacilityRepository;
import com.rlo.reverselogisticsoptimizer.repository.ReturnRepository;
import com.rlo.reverselogisticsoptimizer.repository.VehicleRepository;
import com.rlo.reverselogisticsoptimizer.service.OptimizationHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/optimization")
public class OptimizationController {

    private final RouteOptimizer routeOptimizer;
    private final ReturnRepository returnRepository;
    private final FacilityRepository facilityRepository;
    private final VehicleRepository vehicleRepository;
    private final CostCalculator costCalculator;
    private final OptimizationHistoryService historyService;

    public OptimizationController(
            CostCalculator costCalculator,
            RouteOptimizer routeOptimizer,
            ReturnRepository returnRepository,
            FacilityRepository facilityRepository,
            VehicleRepository vehicleRepository,
            OptimizationHistoryService historyService
    ) {
        this.costCalculator = costCalculator;
        this.routeOptimizer = routeOptimizer;
        this.returnRepository = returnRepository;
        this.facilityRepository = facilityRepository;
        this.vehicleRepository = vehicleRepository;
        this.historyService = historyService;
    }

    @PostMapping("/calculate-cost")
    public Map<String, Double> calculateCost(
            @RequestParam double distance,
            @RequestParam double costPerKm,
            @RequestParam String condition
    ) {

        Vehicle vehicle = new Vehicle();
        vehicle.setCostPerKm(costPerKm);

        Return returnRequest = new Return();
        returnRequest.setCondition(
                ProductCondition.valueOf(condition)
        );

        double transportCost =
                costCalculator.calculateTransportCost(
                        distance,
                        vehicle
                );

        double processingCost =
                costCalculator.calculateProcessingCost(
                        returnRequest
                );

        double totalCost =
                costCalculator.calculateTotalCost(
                        transportCost,
                        processingCost
                );

        return Map.of(
                "transportCost", transportCost,
                "processingCost", processingCost,
                "totalCost", totalCost
        );
    }

    @PostMapping("/optimize/{returnId}")
    public OptimizationResult optimize(
            @PathVariable String returnId
    ) {

        Return returnRequest =
                returnRepository.findById(returnId)
                        .orElseThrow(() ->
                                new RuntimeException("Return not found")
                        );

        List<Facility> facilities =
                facilityRepository.findAll();

        List<Vehicle> vehicles =
                vehicleRepository.findAll();

        OptimizationResult result =
                routeOptimizer.findBestRoute(
                        returnRequest,
                        facilities,
                        vehicles
                );

        returnRequest.setStatus(ReturnStatus.ASSIGNED);
        returnRepository.save(returnRequest);

        OptimizationHistory history =
                OptimizationHistory.builder()
                        .returnId(returnRequest.getId())
                        .facilityId(result.getFacility().getId())
                        .facilityName(result.getFacility().getName())
                        .vehicleId(result.getVehicle().getId())
                        .vehicleNumber(result.getVehicle().getVehicleNumber())
                        .distance(result.getDistance())
                        .transportCost(result.getTransportCost())
                        .processingCost(result.getProcessingCost())
                        .totalCost(result.getTotalCost())
                        .capacityUtilization(result.getCapacityUtilization())
                        .utilizationPenalty(result.getUtilizationPenalty())
                        .finalScore(result.getFinalScore())
                        .build();

        historyService.save(history);

        return result;
    }

    @GetMapping("/history")
    public List<OptimizationHistory> getHistory() {
        return historyService.getAll();
    }

    @GetMapping("/history/{returnId}")
    public List<OptimizationHistory> getHistoryByReturn(
            @PathVariable String returnId
    ) {
        return historyService.getByReturnId(returnId);
    }
}
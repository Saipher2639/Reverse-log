package com.rlo.reverselogisticsoptimizer.optimization;

import com.rlo.reverselogisticsoptimizer.entity.Facility;
import com.rlo.reverselogisticsoptimizer.entity.Return;
import com.rlo.reverselogisticsoptimizer.entity.Vehicle;
import com.rlo.reverselogisticsoptimizer.enums.FacilityType;
import com.rlo.reverselogisticsoptimizer.enums.ProductCondition;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteOptimizer {

    private final HaversineCalculator haversineCalculator;
    private final CostCalculator costCalculator;

    public RouteOptimizer(
            CostCalculator costCalculator,
            HaversineCalculator haversineCalculator
    ) {
        this.costCalculator = costCalculator;
        this.haversineCalculator = haversineCalculator;
    }

    public OptimizationResult findBestRoute(
            Return returnRequest,
            List<Facility> facilities,
            List<Vehicle> vehicles
    ) {

        double productWeight =
                returnRequest.getProduct().getWeight();

        double bestScore = Double.MAX_VALUE;

        Facility bestFacility = null;
        Vehicle bestVehicle = null;

        double bestDistance = 0;
        double bestTransportCost = 0;
        double bestProcessingCost = 0;
        double bestTotalCost = 0;
        double bestUtilization = 0;
        double bestPenalty = 0;

        for (Facility facility : facilities) {

            if (!isCompatible(
                    returnRequest.getCondition(),
                    facility.getType()
            )) {
                continue;
            }

            double remainingCapacity =
                    facility.getCapacity()
                            - facility.getCurrentLoad();

            if (remainingCapacity < productWeight) {
                continue;
            }

            double distance =
                    haversineCalculator.calculateDistance(
                            returnRequest.getLatitude(),
                            returnRequest.getLongitude(),
                            facility.getLatitude(),
                            facility.getLongitude()
                    );

            double processingCost =
                    costCalculator.calculateProcessingCost(
                            returnRequest
                    );

            for (Vehicle vehicle : vehicles) {

                if (!vehicle.isAvailable()) {
                    continue;
                }

                if (vehicle.getCapacity() < productWeight) {
                    continue;
                }

                double transportCost =
                        costCalculator.calculateTransportCost(
                                distance,
                                vehicle
                        );

                double totalCost =
                        costCalculator.calculateTotalCost(
                                transportCost,
                                processingCost
                        );

                double utilization =
                        (facility.getCurrentLoad() + productWeight)
                                / facility.getCapacity();

                double utilizationPenalty = 0;

                if (utilization > 0.80) {
                    utilizationPenalty =
                            totalCost * (utilization - 0.80);
                }

                double finalScore =
                        totalCost + utilizationPenalty;

                if (finalScore < bestScore) {

                    bestScore = finalScore;

                    bestFacility = facility;
                    bestVehicle = vehicle;

                    bestDistance = distance;
                    bestTransportCost = transportCost;
                    bestProcessingCost = processingCost;
                    bestTotalCost = totalCost;
                    bestUtilization = utilization;
                    bestPenalty = utilizationPenalty;
                }
            }
        }

        if (bestFacility == null) {
            throw new IllegalStateException(
                    "No feasible route found"
            );
        }

        return new OptimizationResult(
                bestFacility,
                bestVehicle,
                bestDistance,
                bestTransportCost,
                bestProcessingCost,
                bestTotalCost,
                bestUtilization,
                bestPenalty,
                bestScore
        );
    }

    private boolean isCompatible(
            ProductCondition condition,
            FacilityType facilityType
    ) {

        return switch (condition) {

            case NEW, RESALABLE ->
                    facilityType == FacilityType.WAREHOUSE;

            case REPAIRABLE ->
                    facilityType == FacilityType.REFURBISHMENT_CENTER;

            case RECYCLABLE, DISPOSE ->
                    facilityType == FacilityType.RECYCLING_CENTER;
        };
    }
}
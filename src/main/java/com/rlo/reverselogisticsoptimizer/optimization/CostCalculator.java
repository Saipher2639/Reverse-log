package com.rlo.reverselogisticsoptimizer.optimization;

import com.rlo.reverselogisticsoptimizer.entity.Return;
import com.rlo.reverselogisticsoptimizer.entity.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class CostCalculator {

    private static final double RESALABLE_PROCESSING_COST = 500;
    private static final double REPAIRABLE_PROCESSING_COST = 1500;
    private static final double RECYCLABLE_PROCESSING_COST = 700;
    private static final double DISPOSAL_PROCESSING_COST = 1000;
    private static final double NEW_PROCESSING_COST = 200;

    public double calculateTransportCost(
            double distance,
            Vehicle vehicle
    ) {
        return distance * vehicle.getCostPerKm();
    }

    public double calculateProcessingCost(
            Return returnRequest
    ) {
        return switch (returnRequest.getCondition()) {

            case RESALABLE -> RESALABLE_PROCESSING_COST;
            case REPAIRABLE -> REPAIRABLE_PROCESSING_COST;
            case RECYCLABLE -> RECYCLABLE_PROCESSING_COST;
            case DISPOSE -> DISPOSAL_PROCESSING_COST;
            case NEW -> NEW_PROCESSING_COST;
        };
    }

    public double calculateTotalCost(
            double transportCost,
            double processingCost
    ) {
        return transportCost + processingCost;
    }
}
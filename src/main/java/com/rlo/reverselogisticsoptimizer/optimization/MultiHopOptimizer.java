package com.rlo.reverselogisticsoptimizer.optimization;

import com.rlo.reverselogisticsoptimizer.entity.Facility;
import com.rlo.reverselogisticsoptimizer.entity.Return;
import com.rlo.reverselogisticsoptimizer.entity.Vehicle;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MultiHopOptimizer {

    private final HaversineCalculator haversineCalculator;
    private final CostCalculator costCalculator;

    public MultiHopOptimizer(
            HaversineCalculator haversineCalculator,
            CostCalculator costCalculator
    ) {
        this.haversineCalculator = haversineCalculator;
        this.costCalculator = costCalculator;
    }

    public MultiHopRouteResult buildRoute(
            Return returnRequest,
            Facility collectionCenter,
            Facility recoveryCenter,
            Vehicle vehicle
    ) {

        List<RouteSegment> segments = new ArrayList<>();

        // Customer -> Collection Center
        double firstDistance =
                haversineCalculator.calculateDistance(
                        returnRequest.getLatitude(),
                        returnRequest.getLongitude(),
                        collectionCenter.getLatitude(),
                        collectionCenter.getLongitude()
                );

        double firstCost =
                costCalculator.calculateTransportCost(
                        firstDistance,
                        vehicle
                );

        segments.add(
                new RouteSegment(
                        returnRequest.getCustomerLocation(),
                        collectionCenter.getName(),
                        firstDistance,
                        firstCost,
                        vehicle
                )
        );

        // Collection Center -> Recovery Center
        double secondDistance =
                haversineCalculator.calculateDistance(
                        collectionCenter.getLatitude(),
                        collectionCenter.getLongitude(),
                        recoveryCenter.getLatitude(),
                        recoveryCenter.getLongitude()
                );

        double secondCost =
                costCalculator.calculateTransportCost(
                        secondDistance,
                        vehicle
                );

        segments.add(
                new RouteSegment(
                        collectionCenter.getName(),
                        recoveryCenter.getName(),
                        secondDistance,
                        secondCost,
                        vehicle
                )
        );

        double totalDistance =
                firstDistance + secondDistance;

        double totalCost =
                firstCost + secondCost;

        return new MultiHopRouteResult(
                segments,
                totalDistance,
                totalCost
        );
    }
}
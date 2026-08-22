package com.rlo.reverselogisticsoptimizer.optimization;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MultiHopRouteResult {

    private List<RouteSegment> segments;

    private double totalDistance;

    private double totalCost;
}
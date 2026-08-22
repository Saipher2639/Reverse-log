package com.rlo.reverselogisticsoptimizer.optimization;

import com.rlo.reverselogisticsoptimizer.entity.Facility;
import com.rlo.reverselogisticsoptimizer.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptimizationResult {

    private Facility facility;

    private Vehicle vehicle;

    private double distance;

    private double transportCost;

    private double processingCost;

    private double totalCost;

    private double capacityUtilization;

    private double utilizationPenalty;

    private double finalScore;
}
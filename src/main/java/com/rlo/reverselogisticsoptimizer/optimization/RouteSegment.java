package com.rlo.reverselogisticsoptimizer.optimization;

import com.rlo.reverselogisticsoptimizer.entity.Facility;
import com.rlo.reverselogisticsoptimizer.entity.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RouteSegment {

    private String from;
    private String to;

    private double distance;
    private double cost;

    private Vehicle vehicle;
}
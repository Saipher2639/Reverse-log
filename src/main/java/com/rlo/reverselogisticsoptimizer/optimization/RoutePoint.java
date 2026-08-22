package com.rlo.reverselogisticsoptimizer.optimization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoutePoint {

    private String name;

    private double latitude;

    private double longitude;
}